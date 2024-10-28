/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Historial;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Stack;
/**
 *
 * @author mynit
 */
public class NavigationManager {
    private static NavigationManager instance;
    private Stack<FrameState> navigationStack;
    private Stack<FrameState> forwardStack;

    // Clase interna para mantener el estado de los frames
    private static class FrameState {
        JFrame frame;
        java.awt.Point location;
        java.awt.Dimension size;
        int state;

        FrameState(JFrame frame) {
            this.frame = frame;
            this.location = frame.getLocation();
            this.size = frame.getSize();
            this.state = frame.getExtendedState();
        }

        void restore() {
            frame.setLocation(location);
            frame.setSize(size);
            frame.setExtendedState(state);
        }
    }

    private NavigationManager() {
        navigationStack = new Stack<>();
        forwardStack = new Stack<>();
    }

    public static NavigationManager getInstance() {
        if (instance == null) {
            instance = new NavigationManager();
        }
        return instance;
    }

    public void pushFrame(JFrame frame) {
        SwingUtilities.invokeLater(() -> {
            if (!navigationStack.isEmpty()) {
                FrameState currentState = navigationStack.peek();
                currentState.frame.setVisible(false);
            }
            
            FrameState newState = new FrameState(frame);
            navigationStack.push(newState);
            forwardStack.clear();
            
            frame.setVisible(true);
            frame.toFront();
            frame.requestFocus();
        });
    }

    public void goBack() {
        SwingUtilities.invokeLater(() -> {
            if (navigationStack.size() > 1) {
                FrameState currentState = navigationStack.pop();
                currentState.frame.setVisible(false);
                
                forwardStack.push(currentState);
                
                FrameState previousState = navigationStack.peek();
                previousState.restore();
                previousState.frame.setVisible(true);
                previousState.frame.toFront();
                previousState.frame.requestFocus();
            }
        });
    }

    public void goForward() {
        SwingUtilities.invokeLater(() -> {
            if (!forwardStack.isEmpty()) {
                if (!navigationStack.isEmpty()) {
                    FrameState currentState = navigationStack.peek();
                    currentState.frame.setVisible(false);
                }
                
                FrameState nextState = forwardStack.pop();
                navigationStack.push(nextState);
                nextState.restore();
                nextState.frame.setVisible(true);
                nextState.frame.toFront();
                nextState.frame.requestFocus();
            }
        });
    }

    public boolean canGoBack() {
        return navigationStack.size() > 1;
    }

    public boolean canGoForward() {
        return !forwardStack.isEmpty();
    }

    public void clearStack() {
        SwingUtilities.invokeLater(() -> {
            while (!navigationStack.isEmpty()) {
                FrameState state = navigationStack.pop();
                state.frame.dispose();
            }
            while (!forwardStack.isEmpty()) {
                FrameState state = forwardStack.pop();
                state.frame.dispose();
            }
        });
    }
}