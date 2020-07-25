package processor;

import java.util.Scanner;

class Matrix {
    private static double[][] arr;
    private static int ordX;
    private static int ordY;
    public static int mult;
    public static int doubleOrInt = 0;

    public static void setDoubleOrInt(int doubleOrInt) {
        Matrix.doubleOrInt = doubleOrInt;
    }

    public static void gaussian(double[][] a, int[] index) {
        int n = index.length;
        double[] c = new double[n];
        for (int i=0; i < n; ++i) {
            index[i] = i;
        }
        for (int i = 0; i < n; ++i) {
            double c1 = 0;
            for (int j = 0; j < n; ++j) {
                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }
        int k = 0;
        for (int j = 0; j < n - 1; ++j) {
            double pi1 = 0;
            for (int i = j; i < n; ++i) {
                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];
                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i = j + 1; i < n; ++i) {
                double pj = a[index[i]][j] / a[index[j]][j];
                a[index[i]][j] = pj;
                for (int l = j + 1; l < n; ++l) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    public static void invers(NewMatrix m) {
        int n = m.ordX;
        arr = new double[m.getOrdY()][m.getOrdX()];
        ordX = m.getOrdY();
        ordY = m.getOrdX();

        double[][] b = new double[n][n];
        int[] index = new int[n];
        for (int i = 0; i < n; ++i) {
            b[i][i] = 1;
        }
        gaussian(m.arr, index);
        for (int i = 0; i < n - 1; ++i) {
            for (int j = i + 1; j < n; ++j) {
                for (int k = 0; k < n; ++k) {
                    b[index[j]][k] -= m.arr[index[j]][i] * b[index[i]][k];
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            arr[n-1][i] = b[index[n-1]][i] / m.arr[index[n-1]][n-1];
            for (int j = n - 2; j >= 0; --j) {
                arr[j][i] = b[index[j]][i];
                for (int k = j + 1; k < n; ++k) {
                    arr[j][i] -= m.arr[index[j]][k]*arr[k][i];
                }
                arr[j][i] /= m.arr[index[j]][j];
            }
        }
        doubleOrInt = 2;
        printArr();
    }

    public static double determinant(double[][] array, int n) {
        double result = 0;
        if (n == 1) {
            result = array[0][0];
        } else if (n == 2) {
            result = array[0][0] * array[1][1] - array[1][0] * array[0][1];
        } else {
            for (int i = 0; i < n; i++) {
                result += Math.pow(-1, i) * array[0][i] * determinant(coFactor(array, n, 0, i), n - 1);
            }
        }
        return result;
    }

    public static double[][] coFactor(double[][] array, int n, int actualRow, int actualColumn) {
        double[][] coFactor = new double[n - 1][n - 1];
        int row = 0, col = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++)
                if (i != actualRow && j != actualColumn) {
                    coFactor[row][col] = array[i][j];
                    col++;
                }
            if (col > n - 2) {
                row++;
                col = 0;
            }
        }
        return coFactor;
    }

    public static void rotateGoriz(NewMatrix m) {
        arr = new double[m.getOrdY()][m.getOrdX()];
        ordX = m.getOrdY();
        ordY = m.getOrdX();

        for (int i = 0; i < ordY; i++) {
            if (ordX >= 0) System.arraycopy(m.arr[ordY - i - 1], 0, arr[i], 0, ordX);
        }
        System.out.println("The result is:");
        printArr();
    }

    public static void rotateVert(NewMatrix m) {
        arr = new double[m.getOrdY()][m.getOrdX()];
        ordX = m.getOrdY();
        ordY = m.getOrdX();

        for (int i = 0; i < ordY; i++) {
            for (int j = 0; j < ordX; j++) {
                arr[i][j] = m.arr[i][ordX - j - 1];
            }
        }
        System.out.println("The result is:");
        printArr();
    }

    public static void rotateSideDial(NewMatrix m) {
        arr = new double[m.getOrdX()][m.getOrdY()];
        ordX = m.getOrdY();
        ordY = m.getOrdX();
        for (int i = 0; i < ordX; i++) {
            for (int j = 0; j < ordY; j++) {
                arr[j][i] = m.arr[ordX - i - 1][ordY - j - 1];
            }
        }
        System.out.println("The result is:");
        printArr();
    }

    public static void rotateMainDial(NewMatrix m) {
        arr = new double[m.getOrdX()][m.getOrdY()];
        ordX = m.getOrdY();
        ordY = m.getOrdX();
        for (int i = 0; i < m.getOrdY(); i++) {
            for (int j = 0; j < m.getOrdX(); j++) {
                arr[j][i] = m.arr[i][j];
            }
        }
        System.out.println("The result is:");
        printArr();
    }

    public static void multMatrix(NewMatrix m1, NewMatrix m2) {
        arr = new double[m1.getOrdY()][m2.getOrdX()];
        ordX = m2.getOrdX();
        ordY = m1.getOrdY();
        for(int i = 0; i < m1.getOrdY(); i++) {
            for (int j = 0; j < m2.getOrdX(); j++) {
                arr[i][j] = 0;
                for (int k = 0; k < m1.getOrdX(); k++)
                    arr[i][j] += m1.getArr()[i][k] * m2.getArr()[k][j];
            }
        }
        System.out.println("The multiplication result is:");
        printArr();
    }

    public static void multMatrixToConst(NewMatrix m) {
        arr = m.getArr();
        ordX = m.getOrdX();
        ordY = m.getOrdY();
        for (int i = 0; i < m.getOrdY(); i++) {
            for (int j = 0; j < m.getOrdX(); j++) {
                arr[i][j] = m.getArr()[i][j] * mult;
            }
        }
        System.out.println("The multiplication to a constant result is:");
        printArr();
    }

    public static void getResultSum(NewMatrix m1, NewMatrix m2) {
        arr = m1.getArr();
        ordX = m1.getOrdX();
        ordY = m1.getOrdY();
        for (int i = 0; i < m1.getOrdY(); i++) {
            for (int j = 0; j < m1.getOrdX(); j++) {
                arr[i][j] = m1.getArr()[i][j] + m2.getArr()[i][j];
            }
        }
        System.out.println("Adding result is:");
        printArr();
    }

    public static void printArr() {
        if (doubleOrInt == 0) {
            for (int i = 0; i < ordY; i++) {
                for (int j = 0; j < ordX; j++) {
                    System.out.print((int) arr[i][j] + " ");
                }
                System.out.println();
            }
        } else if (doubleOrInt == 2) {
            for (int i = 0; i < ordY; i++) {
                for (int j = 0; j < ordX; j++) {
                    if (arr[i][j] == 0.0) {
                        System.out.printf("% 5.0f ", arr[i][j]);
                    } else {
                        System.out.printf("% 4.2f ", arr[i][j]);
                    }
                }
                System.out.println();
            }
        } else {
            for (int i = 0; i < ordY; i++) {
                for (int j = 0; j < ordX; j++) {
                    System.out.print(arr[i][j] + " ");
                }
                System.out.println();
            }
        }
        System.out.println();
    }

    public static int getMult() {
        return mult;
    }

    public static void setMult(int mult) {
        Matrix.mult = mult;
    }
}

class NewMatrix extends Matrix {
    int ordX;
    int ordY;
    double[][] arr;

    public double[][] getArr() {
        return arr;
    }

    public void setArr(double[][] arr) {
        this.arr = arr;
    }

    public int getOrdX() {
        return ordX;
    }

    public int getOrdY() {
        return ordY;
    }

    public void setOrdX(int ordX) {
        this.ordX = ordX;
    }

    public void setOrdY(int ordY) {
        this.ordY = ordY;
    }
}

public class Main {

    public static double[][] fillMatrix(NewMatrix matrix, Scanner sc) {
        System.out.println("Enter matrix:");
        double[][] arr = new double[matrix.getOrdY()][matrix.getOrdX()];
        int count = 0;
        for (int i = 0; i < matrix.getOrdY(); i++) {
            StringBuilder str = new StringBuilder();
            str.append(sc.nextLine());
            for (int j = 0; j < str.length(); j++) {
                if (str.charAt(j) == '.') {
                    Matrix.setDoubleOrInt(1);
                }
            }
            String[] tmp = str.toString().split(" ");
            if (tmp.length != matrix.getOrdX()) {
                System.out.println("ERROR, wrong input");
                System.exit(1);
            }
            for (int j = 0; j < tmp.length; j++) {
                arr[i][j] = Double.parseDouble(tmp[j]);
            }
        }
        return arr;
    }

    public static boolean getMatrix(NewMatrix matrix, int i) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        String[] splitStr = str.split(" ");
        if (splitStr.length != 2) {
            System.out.println("ERROR, wrong input");
            System.exit(1);
        }
        if (i == 1) {
            System.out.println("Enter first matrix:");
        } else if (i == 2) {
            System.out.println("Enter second matrix:");
        }
        matrix.setOrdY(Integer.parseInt(splitStr[0]));
        matrix.setOrdX(Integer.parseInt(splitStr[1]));
        if (i == 3) {
            if (matrix.getOrdX() != matrix.getOrdY()) {
                System.out.println("Invalid input!\n");
                return false;
            }
        }
        matrix.setArr(fillMatrix(matrix, sc));
        return true;
    }

    public static void checkMatrix(NewMatrix m1, NewMatrix m2) {
        if (m1.getOrdY() != m2.getOrdY() && m1.getOrdX() != m2.getOrdX()) {
            System.out.println("ERROR");
            System.exit(0);
        }
    }

    public static void transposeStart(NewMatrix m) {
        System.out.print("\n" +
                "1. Main diagonal\n" +
                "2. Side diagonal\n" +
                "3. Vertical line\n" +
                "4. Horizontal line\n" +
                "Your choice: > ");
        switch (new Scanner(System.in).nextInt()) {
            case 1:
                System.out.print("Enter matrix size: > ");
                getMatrix(m, 0);
                Matrix.rotateMainDial(m);
                break;
            case 2:
                System.out.print("Enter matrix size: > ");
                getMatrix(m, 0);
                Matrix.rotateSideDial(m);
                break;
            case 3:
                System.out.print("Enter matrix size: > ");
                getMatrix(m, 0);
                Matrix.rotateVert(m);
                break;
            case 4:
                System.out.print("Enter matrix size: > ");
                getMatrix(m, 0);
                Matrix.rotateGoriz(m);
                break;
            default:
                System.out.println("Undefined operation");
        }

    }

    public static void main(String[] args) {
        int oper;
        NewMatrix m1 = new NewMatrix();
        NewMatrix m2 = new NewMatrix();
        while (true) {
            System.out.print("1. Add matrices\n" +
                    "2. Multiply matrix to a constant\n" +
                    "3. Multiply matrices\n" +
                    "4. Transpose matrix\n" +
                    "5. Calculate a determinant\n" +
                    "6. Inverse matrix\n" +
                    "0. Exit\n" +
                    "Your choice: ");
            oper = new Scanner(System.in).nextInt();
            switch (oper) {
                case 1:
                    System.out.print("Enter size of first matrix: > ");
                    getMatrix(m1, 1);
                    System.out.print("Enter size of second matrix: > ");
                    getMatrix(m2, 2);
                    checkMatrix(m1, m2);
                    Matrix.getResultSum(m1, m2);
                    break;
                case 2:
                    System.out.print("Enter size of matrix: > ");
                    getMatrix(m1, 0);
                    System.out.print("Enter a constant: > ");
                    Matrix.setMult(new Scanner(System.in).nextInt());
                    Matrix.multMatrixToConst(m1);
                    break;
                case 3:
                    System.out.print("Enter size of first matrix: > ");
                    getMatrix(m1, 1);
                    System.out.print("Enter size of second matrix: > ");
                    getMatrix(m2, 2);
                    Matrix.multMatrix(m1, m2);
                    break;
                case 4:
                    transposeStart(m1);
                    break;
                case 5:
                    System.out.print("Enter matrix size: ");
                    getMatrix(m1, 0);
                    double determinant = Matrix.determinant(m1.arr, m1.ordX);
                    System.out.println("The result is:\n" + determinant);
                    System.out.println();
                    break;
                case 6:
                    System.out.print("Enter matrix size: ");
                    getMatrix(m1, 3);
                    Matrix.invers(m1);
                    break;
                case 0:
                    System.exit(0);
                default:
                    System.out.println("Undefined operation");
            }
        }
    }
}
