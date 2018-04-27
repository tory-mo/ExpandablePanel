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
    private boolean defaultHeaderSeparator = true;
    private boolean defaultHeaderSeparatorExpanded = true;
    private boolean defaultContentSeparator = true;
    private int collapsedIcon;
    private int expandedIcon;
    private boolean headerIcon;
    private String headerText;
    private int separatorsColor;
    private float separatorsAlpha;

    private int mContentHeight = 0;
    private int mHeaderHeight = 0;
    private int mSeparatorsHeight = 0;

    private TextView vHeaderLabel;
    private ImageView vHeaderIcon;
    private RelativeLayout vHeader;
    private LinearLayout vContent;
    private View vSeparator1;
    private View vSeparator2;

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
        vSeparator1 = findViewById(R.id.vSeparator1);
        vSeparator2 = findViewById(R.id.vSeparator2);

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

        defaultHeaderSeparator = a.getBoolean(R.styleable.ExpandablePanelLayout_header_separator, defaultHeaderSeparator);
        defaultContentSeparator = a.getBoolean(R.styleable.ExpandablePanelLayout_content_separator, defaultContentSeparator);
        defaultHeaderSeparatorExpanded = a.getBoolean(R.styleable.ExpandablePanelLayout_header_separator_expanded, defaultHeaderSeparatorExpanded);

        if(defaultHeaderSeparator || defaultContentSeparator || defaultExpanded) {
            mSeparatorsHeight = (int) getResources().getDimension(R.dimen.separator_height);
            mSeparatorsHeight = (int)a.getDimension(R.styleable.ExpandablePanelLayout_separators_height, mSeparatorsHeight);

            separatorsColor = getResources().getColor(R.color.separator_color);
            separatorsColor = a.getColor(R.styleable.ExpandablePanelLayout_separators_color, separatorsColor);

            separatorsAlpha = 0.2f;
//            separatorsAlpha = getResources().getDimension(R.dimen.separator_alpha);
//            separatorsAlpha = (int)a.getDimension(R.styleable.ExpandablePanelLayout_separators_alpha, separatorsAlpha);
        }

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

    private void hideShowSeparators(boolean expanded){
        if((!expanded && defaultHeaderSeparator) || (expanded && defaultHeaderSeparatorExpanded)){
            vSeparator1.setBackgroundColor(separatorsColor);
            vSeparator1.setAlpha(separatorsAlpha);
            vSeparator1.setVisibility(VISIBLE);
        }else{
            vSeparator1.setVisibility(GONE);
        }

        if(defaultContentSeparator){
            vSeparator2.setBackgroundColor(separatorsColor);
            vSeparator2.setAlpha(separatorsAlpha);
            vSeparator2.setVisibility(VISIBLE);
        }else{
            vSeparator2.setVisibility(GONE);
        }
    }

    private void initState(){
        toggle();
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
        hideShowSeparators(!defaultExpanded);

        int startHeight = mHeaderHeight;
        int targetHeight = mContentHeight + mHeaderHeight;
        if(defaultHeaderSeparator && defaultContentSeparator){
            targetHeight += mSeparatorsHeight * 2;
            startHeight += mSeparatorsHeight;
        }else if(defaultHeaderSeparator || defaultContentSeparator){
            targetHeight += mSeparatorsHeight;
            startHeight += mSeparatorsHeight;
        }

        Animation a = new ExpandAnimation(startHeight, targetHeight);
        a.setDuration(duration);
        startAnimation(a);

        defaultExpanded = true;
    }

    public void collapse() {
        collapse(duration);
    }

    public void collapse(long duration) {
        if(headerIcon)
            vHeaderIcon.setImageResource(collapsedIcon);
        vContent.setVisibility(INVISIBLE);

        hideShowSeparators(!defaultExpanded);

//        final ViewGroup.LayoutParams layoutParams = getLayoutParams();
//        if (layoutParams != null) {
//            layoutParams.height = mHeaderHeight;
//            setLayoutParams(layoutParams);
//        }

        //final int targetHeight = mContentHeight + mHeaderHeight+mSeparatorsHeight;

        int startHeight = mHeaderHeight;
        int targetHeight = mContentHeight + mHeaderHeight;
        if(defaultHeaderSeparator && defaultContentSeparator){
            targetHeight += mSeparatorsHeight * 2;
            startHeight += mSeparatorsHeight;
        }else if(defaultHeaderSeparator || defaultContentSeparator){
            targetHeight += mSeparatorsHeight;
            startHeight += mSeparatorsHeight;
        }

        Animation a = new ExpandAnimation(targetHeight, startHeight);
        a.setDuration(duration);
        startAnimation(a);

        defaultExpanded = false;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isExpanded() {
        return defaultExpanded;
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
