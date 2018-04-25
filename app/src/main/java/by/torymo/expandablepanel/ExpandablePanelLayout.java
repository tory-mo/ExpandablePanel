package by.torymo.expandablepanel;


import android.animation.TimeInterpolator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandablePanelLayout extends LinearLayout implements ExpandablePanel{

    private int duration;
    private boolean defaultExpanded;
    private int defaultChildIndex;

    private TextView vHeaderLabel;
    private ImageView vHeaderIcon;
    private RelativeLayout vHeader;
    private LinearLayout vContent;

    public ExpandablePanelLayout(Context context) {
        super(context, null);
    }

    public ExpandablePanelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public ExpandablePanelLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ExpandablePanelLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context, attrs, defStyleAttr);
    }

    private void init(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(inflater == null) return;

        inflater.inflate(R.layout.panel_header, this);

        vHeaderLabel = findViewById(R.id.vHeaderLabel);
        vHeaderIcon = findViewById(R.id.vHeaderIcon);
        vHeader = findViewById(R.id.vHeader);
        vContent = findViewById(R.id.vContent);

        assignClickHandlers();
    }

    private void assignClickHandlers()
    {
        vHeader.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                if(eventHandler != null){
//                    Date[] startEnd = getCurrentMonthStartEnd();
//                    eventHandler.onMonthChanged(startEnd[0], startEnd[1]);
//                }
                if(isExpanded()) collapse();
                else expand();
            }
        });
    }

    @Override
    public void toggle() {

    }

    @Override
    public void toggle(long duration, @Nullable TimeInterpolator interpolator) {

    }

    @Override
    public void expand() {

    }

    @Override
    public void expand(long duration, @Nullable TimeInterpolator interpolator) {

    }

    @Override
    public void collapse() {

    }

    @Override
    public void collapse(long duration, @Nullable TimeInterpolator interpolator) {

    }

    @Override
    public void setListener(@NonNull ExpandablePanelListener listener) {

    }

    @Override
    public void setDuration(int duration) {

    }

    @Override
    public void setExpanded(boolean expanded) {

    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public void setInterpolator(@NonNull TimeInterpolator interpolator) {

    }
}
