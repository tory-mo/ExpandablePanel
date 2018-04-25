package by.torymo.expandablepanel;


import android.animation.TimeInterpolator;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public interface ExpandablePanel {

    int DEFAULT_DURATION = 300;
    boolean DEFAULT_EXPANDED = false;

    int HORIZONTAL = 0;
    int VERTICAL = 1;

    @IntDef({HORIZONTAL, VERTICAL})
    @interface Orientation {
    }

    void toggle();
    void toggle(final long duration, @Nullable final TimeInterpolator interpolator);

    void expand();
    void expand(final long duration, @Nullable final TimeInterpolator interpolator);

    void collapse();
    void collapse(final long duration, @Nullable final TimeInterpolator interpolator);

    void setListener(@NonNull final ExpandablePanelListener listener);

    void setDuration(final int duration);

    void setExpanded(final boolean expanded);

    boolean isExpanded();

    void setInterpolator(@NonNull final TimeInterpolator interpolator);
}
