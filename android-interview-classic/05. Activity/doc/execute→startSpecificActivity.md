## execute→startSpecificActivity

#### ActivityStarter.java

``` java
/**
 * 根据前面提供的请求参数解析必要的信息，并执行开始启动Activity旅程的请求。
 */
int execute() {
    try {
        // ...
        
        synchronized (mService.mGlobalLock) {
            // ...
            
            // 执行开启Activity的请求
            res = executeRequest(mRequest);

            // ...
            return getExternalResult(res);
        }
    } finally {
        onExecutionComplete();
    }
}
```

``` java
/**
 * 执行Activity启动请求，并开始启动一个Activity的旅程。
 * 这里从执行几项初步检查开始。
 * 正常的Activity启动流将通过startActivityUnchecked到startActivityInner。
 */
private int executeRequest(Request request) {

      // ...

      final ActivityRecord r = new ActivityRecord.Builder(mService)
                .setCaller(callerApp)
                .setLaunchedFromPid(callingPid)
                .setLaunchedFromUid(callingUid)
                .setLaunchedFromPackage(callingPackage)
                .setLaunchedFromFeature(callingFeatureId)
                .setIntent(intent)
                .setResolvedType(resolvedType)
                .setActivityInfo(aInfo)
                .setConfiguration(mService.getGlobalConfiguration())
                .setResultTo(resultRecord)
                .setResultWho(resultWho)
                .setRequestCode(requestCode)
                .setComponentSpecified(request.componentSpecified)
                .setRootVoiceInteraction(voiceSession != null)
                .setActivityOptions(checkedOptions)
                .setSourceRecord(sourceRecord)
                .build();
    
    // ...

    mLastStartActivityResult = startActivityUnchecked(r, sourceRecord, voiceSession,
            request.voiceInteractor, startFlags, true /* doResume */, checkedOptions, inTask,
            restrictedBgActivity, intentGrants);

    // ...

    return mLastStartActivityResult;
}
```

``` java
/**
 * 在完成大部分初步检查并确认调用者持有必要的权限时启动活动。
 * 这里还确保如果启动不成功，则删除启动活动。
 */
private int startActivityUnchecked(final ActivityRecord r, ActivityRecord sourceRecord,
        IVoiceInteractionSession voiceSession, IVoiceInteractor voiceInteractor,
        int startFlags, boolean doResume, ActivityOptions options, Task inTask,
        boolean restrictedBgActivity, NeededUriGrants intentGrants) {
// ...

try {
    // ...
    result = startActivityInner(r, sourceRecord, voiceSession, voiceInteractor,
            startFlags, doResume, options, inTask, restrictedBgActivity, intentGrants);
} finally {
    // ...
}

// ...

return result;
}
```

``` java
/**
 * 启动一个活动，并确定该活动是应该添加到现有任务的顶部，还是应该向现有活动交付新意图。
 * 还将活动任务操作到请求的或有效的根任务/显示上。
 *
 * 注意:这个方法只能从startActivityUnchecked调用。
 */
int startActivityInner(final ActivityRecord r, ActivityRecord sourceRecord,
        IVoiceInteractionSession voiceSession, IVoiceInteractor voiceInteractor,
        int startFlags, boolean doResume, ActivityOptions options, Task inTask,
        boolean restrictedBgActivity, NeededUriGrants intentGrants) {
    // ...

    mTargetRootTask.startActivityLocked(mStartActivity,
            topRootTask != null ? topRootTask.getTopNonFinishingActivity() : null, newTask,
            mKeepCurTransition, mOptions, sourceRecord);
    if (mDoResume) {
        // 获取栈顶Activity
        final ActivityRecord topTaskActivity =
                mStartActivity.getTask().topRunningActivityLocked();
        if (!mTargetRootTask.isTopActivityFocusable()
                || (topTaskActivity != null && topTaskActivity.isTaskOverlay()
                && mStartActivity != topTaskActivity)) {
            mTargetRootTask.ensureActivitiesVisible(null /* starting */,
                    0 /* configChanges */, !PRESERVE_WINDOWS);
            mTargetRootTask.mDisplayContent.executeAppTransition();
        } else {
            if (mTargetRootTask.isTopActivityFocusable()
                    && !mRootWindowContainer.isTopDisplayFocusedRootTask(mTargetRootTask)) {
                mTargetRootTask.moveToFront("startActivityInner");
            }
            // 恢复聚焦的栈顶Activity
            mRootWindowContainer.resumeFocusedTasksTopActivities(
                    mTargetRootTask, mStartActivity, mOptions, mTransientLaunch);
        }
    }
    
    // ...

    return START_SUCCESS;
}
```

#### RootWindowContainer.java
``` java
boolean resumeFocusedTasksTopActivities(
        Task targetRootTask, ActivityRecord target, ActivityOptions targetOptions,
        boolean deferPause) {
    // ...
    
    boolean result = false;
    if (targetRootTask != null && (targetRootTask.isTopRootTaskInDisplayArea()
            || getTopDisplayFocusedRootTask() == targetRootTask)) {
        // 这里的Task就是ActivityTask
        // 内部调用了mTaskSupervisor.startSpecificActivity(next, true, false);
        result = targetRootTask.resumeTopActivityUncheckedLocked(target, targetOptions,
                deferPause);
    }

    // ...

    return result;
}
```