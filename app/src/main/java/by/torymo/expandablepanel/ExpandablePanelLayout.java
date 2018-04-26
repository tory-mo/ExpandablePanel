package by.torymo.expandablepanel;


import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandablePanelLayout extends LinearLayout{

    private int duration = 300;
    private boolean defaultExpanded = false;
    private boolean defaultHeaderIcon = true;
    private int collapsedIcon;
    private int expandedIcon;
    private boolean headerIcon;
    private String headerText;

    private boolean expanded = defaultExpanded;

    private int mContentHeight = 0;
    private int mHeaderHeight = 0;

    private TextView vHeaderLabel;
    private ImageView vHeaderIcon;
    private RelativeLayout vHeader;
    private LinearLayout vContent;

    private boolean isCreated;

    private float hpt;
    private float hpb;
    private float hpl;
    private float hpr;

    private float cpt;
    private float cpb;
    private float cpl;
    private float cpr;

    private int headerBgColor = 0;
    private int contentBgColor = 0;

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
        headerIcon = a.getBoolean(R.styleable.ExpandablePanelLayout_expandedIcon, defaultHeaderIcon);
        defaultExpanded = a.getBoolean(R.styleable.ExpandablePanelLayout_defaultExpanded, defaultExpanded);

        hpt = a.getDimension(R.styleable.ExpandablePanelLayout_header_paddingTop, 0);
        hpb = a.getDimension(R.styleable.ExpandablePanelLayout_header_paddingBottom, 0);
        hpl = a.getDimension(R.styleable.ExpandablePanelLayout_header_paddingLeft, 0);
        hpr = a.getDimension(R.styleable.ExpandablePanelLayout_header_paddingRight, 0);

        cpt = a.getDimension(R.styleable.ExpandablePanelLayout_content_paddingTop, 0);
        cpb = a.getDimension(R.styleable.ExpandablePanelLayout_content_paddingBottom, 0);
        cpl = a.getDimension(R.styleable.ExpandablePanelLayout_content_paddingLeft, 0);
        cpr = a.getDimension(R.styleable.ExpandablePanelLayout_content_paddingRight, 0);

        contentBgColor = a.getColor(R.styleable.ExpandablePanelLayout_content_backgroundColor, 0);
        headerBgColor = a.getColor(R.styleable.ExpandablePanelLayout_header_backgroundColor, 0);

        a.recycle();


    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isCreated) {
            buildView();
            isCreated = true;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (!isCreated) {
            buildView();
            isCreated = true;
        }
    }

    private void buildView(){
        vHeaderLabel.setText(headerText);

        vHeader.setPadding((int)hpl,(int)hpt, (int)hpr, (int)hpb);
        if(headerBgColor != 0){
            vHeader.setBackgroundColor(headerBgColor);
        }

        mHeaderHeight = vHeader.getHeight();

        vContent.setPadding((int)cpl,(int)cpt, (int)cpr, (int)cpb);
        if(contentBgColor != 0){
            vContent.setBackgroundColor(contentBgColor);
        }

        vHeader.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(isExpanded()) collapse();
                else expand();
            }
        });

        initState();
    }

    private void initState(){
        if(defaultExpanded){
            expand();
        }else {
            collapse();
        }
    }

    public void setContent(View view){
        vContent.removeAllViews();
        vContent.addView(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    public void toggle() {
        if(isExpanded())collapse();
        else expand();
    }

    public void toggle(long duration) {
        if(isExpanded())collapse(duration);
        else expand(duration);
    }

    public void expand() {
        expand(duration);
    }

    public void expand(long duration) {
        if(headerIcon)
            vHeaderIcon.setImageResource(expandedIcon);
        vContent.setVisibility(VISIBLE);

        final int targetHeight = mContentHeight + mHeaderHeight;

        Animation a = new ExpandAnimation(mHeaderHeight, targetHeight);
        a.setDuration(duration);
        startAnimation(a);

        expanded = true;
    }

    public void collapse() {
        collapse(duration);
    }

    public void collapse(long duration) {
        if(headerIcon)
            vHeaderIcon.setImageResource(collapsedIcon);
        vContent.setVisibility(INVISIBLE);

//        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        if (layoutParams != null) {
//            layoutParams.height = mHeaderHeight;
//            setLayoutParams(layoutParams);
//        }

        final int targetHeight = mContentHeight + mHeaderHeight;
        Animation a = new ExpandAnimation(targetHeight, mHeaderHeight);
        a.setDuration(duration);
        startAnimation(a);

        expanded = false;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isExpanded() {
        return expanded;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        vContent.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        int currC = vContent.getMeasuredHeight();


        vHeader.measure(widthMeasureSpec, MeasureSpec.UNSPECIFIED);
        int currH = vHeader.getMeasuredHeight();
        if(currH != mHeaderHeight || currC != mContentHeight){
            mHeaderHeight = currH;
            mContentHeight = currC;
            initState();
        }

        // Then let the usual thing happen
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    private class ExpandAnimation extends Animation {

        private final int mStartHeight;
        private final int mDeltaHeight;

        ExpandAnimation(int startHeight, int endHeight) {
            mStartHeight = startHeight;
            mDeltaHeight = endHeight - startHeight;
        }

        @Override
        protected void applyTransformation(float interpolatedTime,
                                           Transformation t) {
            android.view.ViewGroup.LayoutParams lp = getLayoutParams();
            lp.height = (int) (mStartHeight + mDeltaHeight * interpolatedTime);
            setLayoutParams(lp);
        }

        @Override
        public boolean willChangeBounds() {
            // TODO Auto-generated method stub
            return true;
        }

    }
}
