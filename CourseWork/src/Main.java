import java.io.FileWriter;
import java.io.IOException;

public class Main {

    static double   lam = 1,  // значення констант
            a = 5,
            b = -5,
            C = 5;

    public static double boundaryFunctionConditionsZero(double t) // x=0 -> w(0,t)
    {
        return Math.pow(C * Math.exp(-(lam/a) * (0 + lam*t)) - b/2*lam, -1);
    }

    public static double boundaryFunctionConditionsOne( double t) // x=1 -> w(1,t)
    {
        return Math.pow(C * Math.exp(-(lam/a) * (1 + lam*t)) - b/2*lam, -1);
    }

    public static double initialFunctionConditions(double x) // t = 0 -> w(x,0)
    {
        return Math.pow(C * Math.exp(-(lam/a) * (x)) - b/2*lam, -1);
    }

    public static double countFunction( double x, double t)  //
    {
        return Math.pow(C * Math.exp(-(lam/a) * (x + lam*t)) - b/2*lam, -1);
    }

    public static double countWgridFunction(double rightWi, double leftWi, double Wi, double tau, double hStep)
    {
        return Wi + tau * (2 * ((leftWi - 2 * Wi + rightWi)/(hStep * hStep)) + 6 * Wi * ((rightWi - leftWi)/(2*hStep)));
    }

    public static void main(String[] args) {
        
    }
}
