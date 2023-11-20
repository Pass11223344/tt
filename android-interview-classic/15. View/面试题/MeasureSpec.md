# MeasureSpec

measure specification 测量规范

规定了子View的测量规范。

## int getMode(int measureSpec)

获取测量模式。

1. MeasureSpec.EXACTLY：精确尺寸。对应xxdp或match_parent。
2. MeasureSpec.AT_MOST：至多尺寸。对应wrap_content，即宽高有内容决定。
3. MeasureSpec.UNSPECIFIED：不指定尺寸。



