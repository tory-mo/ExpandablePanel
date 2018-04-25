package by.torymo.expandablepanel;

public interface ExpandablePanelListener {

    void onAnimationStart();

    void onAnimationEnd();

    void onPreOpen();

    void onPreClose();

    void onOpened();

    void onClosed();
}
