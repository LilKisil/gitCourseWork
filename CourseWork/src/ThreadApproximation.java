public class ThreadApproximation extends Thread{
    private double matrix[][];
    private int startIndex;
    private int endIndex;
    private double result;
    private double stepT;
    private double stepX;
    private int id;

    public ThreadApproximation(double[][] matrix, int startIndex, int endIndex, double stepT, double stepX, int id) {
        this.matrix = matrix;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.stepT = stepT;
        this.stepX = stepX;
        this.id = id;
    }

    @Override
    public void run(){
        for(int i = id; i<id+1; i++ ){
            for(int j = startIndex; j<endIndex; j++ ){
               matrix[i][j] = Main.countWgridFunction(matrix[i - 1][j + 1],
                        matrix[i - 1][j - 1], matrix[i - 1][j], stepT, stepX);
            }
        }
    }
}
