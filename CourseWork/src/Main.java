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

    public static int NUNMBER_THREADS = 9;
    public static final int amountOfSteps_X = 10;
    public static final int amountOfSteps_T = 100;

    public static void main(String[] args) throws InterruptedException {
        double stepX = 0.1;
        double stepT = 0.002;

        double[][]  MatrixPutTX = new double[amountOfSteps_T][amountOfSteps_X];
        double[][]  MatrixFormula =  new double [amountOfSteps_T][amountOfSteps_X];

        double Value_X;
        double Value_T = 0.0;

        for (int i = 0; i < amountOfSteps_T; i++)
        {
            Value_X = 0.0;
            for (int j = 0; j < amountOfSteps_X; j++)
            {
                MatrixPutTX[i][j] = countFunction(Value_X, Value_T);
                Value_X += stepX;
            }
            Value_T += stepT;
        }

        Value_X = 0.0;
        for (int i = 0; i < amountOfSteps_X; i++)
        {
            MatrixFormula[0][i] = initialFunctionConditions(Value_X);
            Value_X += stepX;
        }

        Value_T = 0.0;
        for (int i = 0; i < amountOfSteps_T; i++)
        {
            MatrixFormula[i][0] = boundaryFunctionConditionsZero(Value_T);
            MatrixFormula[i][amountOfSteps_X - 1] = boundaryFunctionConditionsOne(Value_T);
            Value_T += stepT;
        }

        for (int i = 1; i < amountOfSteps_T; i++){
            ThreadApproximation TreadArray[] = new ThreadApproximation[NUNMBER_THREADS];
            for(int j = 0; j < NUNMBER_THREADS; j++){
                TreadArray[j] = new ThreadApproximation(MatrixFormula,
                        j==0?1:(amountOfSteps_X-1)/NUNMBER_THREADS * j,
                        j==(NUNMBER_THREADS - 1)?(amountOfSteps_X-1):(amountOfSteps_X-1)/NUNMBER_THREADS * (j + 1),stepT, stepX, i);
                TreadArray[j].start();
            }
            for(int j = 0; j < NUNMBER_THREADS; j++){
                TreadArray[j].join();
            }
        }


    }
}
