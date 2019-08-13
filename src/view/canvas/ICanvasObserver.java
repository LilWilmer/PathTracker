package view.canvas;

import view.canvas.component.Component;

public interface ICanvasObserver 
{
    void componentUpdated(Canvas canv, Component comp);
}
