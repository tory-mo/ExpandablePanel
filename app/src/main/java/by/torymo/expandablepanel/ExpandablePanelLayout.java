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
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandablePanelLayout extends LinearLayout implements ExpandablePanel{

    private int duration;
    private boolean defaultExpanded = false;
    int collapsedIcon;
    int expandedIcon;
    boolean headerIcon;
    String headerText;

    boolean expanded = defaultExpanded;

    private TextView vHeaderLabel;
    private ImageView vHeaderIcon;
    private RelativeLayout vHeader;
    private LinearLayout vContent;

    public ExpandablePanelLayout(Context context) {
        super(context, null);
        init(context, null, 0);
    }

    public ExpandablePanelLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        init(context, attrs, 0);
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

        inflater.inflate(R.layout.expandable_panel, this);

        vHeaderLabel = findViewById(R.id.vHeaderLabel);
        vHeaderIcon = findViewById(R.id.vHeaderIcon);
        vHeader = findViewById(R.id.vHeader);
        vContent = findViewById(R.id.vContent);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ExpandablePanelLayout, defStyleAttr, 0);

        headerText = a.getString(R.styleable.ExpandablePanelLayout_headerText);
        collapsedIcon = a.getResourceId(R.styleable.ExpandablePanelLayout_collapsedIcon, R.drawable.chevron_down);
        expandedIcon = a.getResourceId(R.styleable.ExpandablePanelLayout_expandedIcon, R.drawable.chevron_up);
        headerIcon = a.getBoolean(R.styleable.ExpandablePanelLayout_expandedIcon, true);
        defaultExpanded = a.getBoolean(R.styleable.ExpandablePanelLayout_defaultExpanded, false);


        if(defaultExpanded){
            expand();
        }else {
            collapse();
        }
        vHeaderLabel.setText(headerText);

        a.recycle();

        assignClickHandlers();
    }

    public void setContent(View view){
        vContent.removeAllViews();
        vContent.addView(view);
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
        if(isExpanded())collapse();
        else expand();
    }

    @Override
    public void toggle(long duration, @Nullable TimeInterpolator interpolator) {

    }

    @Override
    public void expand() {
        if(isExpanded()) return;
        if(headerIcon)
            vHeaderIcon.setImageResource(expandedIcon);
        vContent.setVisibility(VISIBLE);

        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = vContent.getHeight() + vHeader.getHeight();
            setLayoutParams(layoutParams);
        }

        expanded = true;
    }

    @Override
    public void expand(long duration, @Nullable TimeInterpolator interpolator) {

    }

    @Override
    public void collapse() {
        if(headerIcon)
            vHeaderIcon.setImageResource(collapsedIcon);
        vContent.setVisibility(INVISIBLE);

        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (layoutParams != null) {
            layoutParams.height = vHeader.getHeight();
            setLayoutParams(layoutParams);
        }

        expanded = false;
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
        return expanded;
    }

    @Override
    public void setInterpolator(@NonNull TimeInterpolator interpolator) {

    }
}
