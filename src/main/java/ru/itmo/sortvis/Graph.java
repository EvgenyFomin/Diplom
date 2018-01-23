package ru.itmo.sortvis;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class Graph {
    private static final int N = 20;
    private static final int INFTY = N + 1;
    private int countOfVertexWithCoord = 0;
    Queue<Integer> sqrQueue = new LinkedList();
    Queue<Integer> lineQueue = new LinkedList<>();
    private byte[][] matrix;
    private LinkedList<Integer>[] pairSqr = new LinkedList[N];
    private final List<GraphModelListener> listenerList;
    private int[] coord;

    Graph() {
        listenerList = new ArrayList<>();
        coord = new int[2 * N];
        for (int i = 0; i < coord.length; i++) {
            coord[i] = N + 1;

        }
    }

    void initGraph() {
        matrix = new byte[][]{
                {-1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, -1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0},
                {0, 1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, -1, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, -1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 1, -1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, -1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 1, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {1, 0, 0, 0, 0, 0, 1, 1, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0, 1, 0, 0, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, -1, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, -1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, -1, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, -1},
        };

        for (int i = 0; i < N; i++) {
            pairSqr[i] = new LinkedList<>();

        }

        sqrSearch();
        giveCoordinates();
        graphInitialized();
//        print();
        
    }

    private void sqrSearch() {
        int countOfCoincidences = 0;
        int corner1 = -1, corner2 = -1;
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < N; j++) {
                if (i == j) continue;
                for (int k = 0; k < N; k++) {
                    if (matrix[i][k] == 1 && matrix[j][k] == 1) {
                        countOfCoincidences++;
                        corner1 = corner2;
                        corner2 = k;

                    }

                    if (countOfCoincidences == 2) {
                        pairSqr[i].add(j);
                        pairSqr[i].add(corner1);
                        pairSqr[i].add(corner2);
                        break;

                    }

                }

                countOfCoincidences = 0;
                corner1 = -1;
                corner2 = -1;

            }

        }

    }

    private void giveCoordinates() {
        int firstVertex;
        for (firstVertex = 0; firstVertex < pairSqr.length; firstVertex++) {
            if (!pairSqr[firstVertex].isEmpty()) {
                setCoordX(firstVertex, 0);
                setCoordY(firstVertex, 0);
                sqrQueue.add(firstVertex);
                break;
            }

            if (firstVertex == pairSqr.length - 1) {
                setCoordX(firstVertex, 0);
                setCoordY(firstVertex, 0);
                lineQueue.add(firstVertex);
                break;

            }

        }

        countOfVertexWithCoord = 1;
        while (countOfVertexWithCoord < N) {
            while (!sqrQueue.isEmpty())
                coordToSqr(sqrQueue.poll());
            while (!lineQueue.isEmpty())
                coordToEdge(lineQueue.poll());

        }

    }

    private void coordToSqr(int currentVertex) {
        int countOfVertexWithoutCoord = 0;
        int oppositeVertex = pairSqr[currentVertex].pop(), adjVertex1 = pairSqr[currentVertex].pop(),
                adjVertex2 = pairSqr[currentVertex].pop();

        countOfVertexWithoutCoord += getCoordX(oppositeVertex)/(INFTY) + getCoordX(adjVertex1)/(INFTY)
                + getCoordX(adjVertex2)/(INFTY);

        if (countOfVertexWithoutCoord > 0) {
            if (getCoordX(oppositeVertex) != INFTY) { // Если оппозиционная вершина квадрата имеет координаты
                if (getCoordX(adjVertex1) == INFTY && getCoordX(adjVertex2) == INFTY) { // Если обе смежные вершины не имеют координат

                    setCoordX(pairSqr[currentVertex].get(1), getCoordX(currentVertex));
                    setCoordY(pairSqr[currentVertex].get(1), getCoordY(pairSqr[currentVertex].get(0)));

                    setCoordX(pairSqr[currentVertex].get(2), getCoordX(pairSqr[currentVertex].get(0)));
                    setCoordY(pairSqr[currentVertex].get(2), getCoordY(currentVertex));

                    countOfVertexWithCoord += 2;

                } else if (getCoordX(adjVertex1) != INFTY) { // Если одна смежная вершина имеет координаты

                    if (getCoordX(adjVertex1) == getCoordX(currentVertex)) {
                        setCoordX(adjVertex2, getCoordX(oppositeVertex));
                        setCoordY(adjVertex2, getCoordY(currentVertex));

                    } else {
                        setCoordX(adjVertex2, getCoordX(currentVertex));
                        setCoordY(adjVertex2, getCoordY(oppositeVertex));

                    }

                    countOfVertexWithCoord++;

                } else { // Если другая смежная вершина имеет координаты

                    if (getCoordX(adjVertex2) == getCoordX(currentVertex)) {
                        setCoordX(adjVertex1, getCoordX(oppositeVertex));
                        setCoordY(adjVertex1, getCoordY(currentVertex));

                    } else {
                        setCoordX(adjVertex1, getCoordX(currentVertex));
                        setCoordY(adjVertex1, getCoordY(oppositeVertex));

                    }

                    countOfVertexWithCoord++;

                }
            } else { // Если оппозиционная вершина не имеет координат
                if (getCoordX(adjVertex1) != INFTY && getCoordX(adjVertex2) != INFTY) { // Если обе смежные вершины имеют координаты
                    if (getCoordX(adjVertex1) == getCoordX(currentVertex)) {
                        setCoordX(oppositeVertex, getCoordX(adjVertex2));
                        setCoordY(oppositeVertex, getCoordY(adjVertex1));

                    } else {
                        setCoordX(oppositeVertex, getCoordX(adjVertex1));
                        setCoordY(oppositeVertex, getCoordY(adjVertex2));

                    }

                    countOfVertexWithCoord++;

                } else if (getCoordX(adjVertex1) != INFTY) { // Если одна смежная вершина имеет координаты
                    if (getCoordX(adjVertex1) == getCoordX(currentVertex)) {
                        setCoordY(oppositeVertex, getCoordY(adjVertex1));

                        for (int i = 0; i < N; i++) {
                            if (getCoordX(i) == getCoordX(adjVertex1) - 1 && getCoordY(i) == getCoordY(adjVertex1)) {
                                setCoordX(oppositeVertex, getCoordX(adjVertex1) + 1);
                                break;

                            }

                            if (i == N - 1) {
                                setCoordX(oppositeVertex, getCoordX(adjVertex1) - 1);

                            }

                        }

                    } else {
                        if (getCoordY(adjVertex1) == getCoordY(currentVertex)) {
                            setCoordX(oppositeVertex, getCoordX(adjVertex1));

                            for (int i = 0; i < N; i++) {
                                if (getCoordY(i) == getCoordY(adjVertex1) - 1 && getCoordX(i) == getCoordX(adjVertex1)) {
                                    setCoordY(oppositeVertex, getCoordY(adjVertex1) + 1);
                                    break;

                                }

                                if (i == N - 1) {
                                    setCoordY(oppositeVertex, getCoordY(adjVertex1) - 1);

                                }

                            }

                        }

                    }

                    setCoordX(adjVertex2,
                            (getCoordX(oppositeVertex) == getCoordX(adjVertex1)) ? getCoordX(currentVertex) : getCoordX(oppositeVertex));
                    setCoordY(adjVertex2,
                            (getCoordY(oppositeVertex) == getCoordY(adjVertex1)) ? getCoordY(currentVertex) : getCoordY(oppositeVertex));

                    countOfVertexWithCoord += 2;

                } else if (getCoordX(adjVertex2) != INFTY) { // Если другая смежная вершина имеет координаты
                    if (getCoordX(adjVertex2) == getCoordX(currentVertex)) {
                        setCoordY(oppositeVertex, getCoordY(adjVertex2));

                        for (int i = 0; i < N; i++) {
                            if (getCoordX(i) == getCoordX(adjVertex2) - 1 && getCoordY(i) == getCoordY(adjVertex2)) {
                                setCoordX(oppositeVertex, getCoordX(adjVertex2) + 1);
                                break;

                            }

                            if (i == N - 1) {
                                setCoordX(oppositeVertex, getCoordX(adjVertex2) - 1);

                            }

                        }

                    } else {
                        if (getCoordY(adjVertex2) == getCoordY(currentVertex)) {
                            setCoordX(oppositeVertex, getCoordX(adjVertex2));

                            for (int i = 0; i < N; i++) {
                                if (getCoordY(i) == getCoordY(adjVertex2) - 1 && getCoordX(i) == getCoordX(adjVertex2)) {
                                    setCoordY(oppositeVertex, getCoordY(adjVertex2) + 1);
                                    break;

                                }

                                if (i == N - 1) {
                                    setCoordY(oppositeVertex, getCoordY(adjVertex2) - 1);

                                }

                            }

                        }

                    }

                    setCoordX(adjVertex1,
                            (getCoordX(oppositeVertex) == getCoordX(adjVertex2)) ? getCoordX(currentVertex) : getCoordX(oppositeVertex));
                    setCoordY(adjVertex1,
                            (getCoordY(oppositeVertex) == getCoordY(adjVertex2)) ? getCoordY(currentVertex) : getCoordY(oppositeVertex));

                    countOfVertexWithCoord += 2;

                } else { // Если ни одна смежная вершина не имеет координат
                    setCoordX(adjVertex1, getCoordX(currentVertex));
                    setCoordY(adjVertex2, getCoordY(currentVertex));
                    for (int i = 0; i < coord.length - 1; i += 2) {
                        if (coord[i] == getCoordX(currentVertex) && coord[i + 1] == getCoordY(currentVertex) - 1) {
                            setCoordY(adjVertex1, getCoordY(currentVertex) + 1);
                            break;

                        }

                        if (i == coord.length - 2) {
                            setCoordY(adjVertex1, getCoordY(currentVertex) - 1);

                        }


                    }

                    for (int i = 0; i < coord.length - 1; i += 2) {
                        if (coord[i] == getCoordX(currentVertex) - 1 && coord[i + 1] == getCoordY(currentVertex)) {
                            setCoordX(adjVertex2, getCoordX(currentVertex) + 1);
                            break;

                        }

                        if (i == coord.length - 2) {
                            setCoordX(adjVertex2, getCoordX(currentVertex) - 1);

                        }

                    }

                    setCoordX(oppositeVertex, getCoordX(adjVertex2));
                    setCoordY(oppositeVertex, getCoordY(adjVertex1));

                    countOfVertexWithCoord += 3;

                }

            }

        }

        if (!pairSqr[currentVertex].isEmpty() && !sqrQueue.contains(currentVertex)) {
            sqrQueue.add(currentVertex);

        }

        else if (!lineQueue.contains(currentVertex)) {
            lineQueue.add(currentVertex);

        }

        if (!pairSqr[oppositeVertex].isEmpty() && !sqrQueue.contains(oppositeVertex)) {
            sqrQueue.add(oppositeVertex);

        }

        else if (!lineQueue.contains(oppositeVertex)) {
            lineQueue.add(oppositeVertex);

        }

        if (!pairSqr[adjVertex1].isEmpty() && !sqrQueue.contains(adjVertex1)) {
            sqrQueue.add(adjVertex1);

        }

        else if (!lineQueue.contains(adjVertex1)) {
            lineQueue.add(adjVertex1);

        }

        if (!pairSqr[adjVertex2].isEmpty() && !sqrQueue.contains(adjVertex2)) {
            sqrQueue.add(adjVertex2);

        }

        else if (!lineQueue.contains(adjVertex2)) {
            lineQueue.add(adjVertex2);

        }

    }

    private void coordToEdge(int currentVertex) {
        for (int j = 0; j < N; j++) {
            if (matrix[currentVertex][j] == 1 && getCoordX(j) == INFTY) {
                boolean isFreeLeft = true, isFreeUp = true, isFreeRight = true, isFreeDown = true;
                for (int i = 0; i < coord.length - 1; i += 2) {
                    if (isFreeLeft && coord[i] == getCoordX(currentVertex) - 1 && coord[i + 1] == getCoordY(currentVertex))
                        isFreeLeft = false;
                    if (isFreeRight && coord[i] == getCoordX(currentVertex) + 1 && coord[i + 1] == getCoordY(currentVertex))
                        isFreeRight = false;
                    if (isFreeUp && coord[i] == getCoordX(currentVertex) && coord[i + 1] == getCoordY(currentVertex) - 1)
                        isFreeUp = false;
                    if (isFreeDown && coord[i] == getCoordX(currentVertex) && coord[i + 1] == getCoordY(currentVertex) + 1)
                        isFreeDown = false;

                }

                if (isFreeLeft) {
                    setCoordX(j, getCoordX(currentVertex) - 1);
                    setCoordY(j, getCoordY(currentVertex));

                }

                else if (isFreeUp) {
                    setCoordX(j, getCoordX(currentVertex));
                    setCoordY(j, getCoordY(currentVertex) - 1);

                }

                else if (isFreeRight) {
                    setCoordX(j, getCoordX(currentVertex) + 1);
                    setCoordY(j, getCoordY(currentVertex));

                }

                else {
                    setCoordX(j, getCoordX(currentVertex));
                    setCoordY(j, getCoordY(currentVertex) + 1);

                }

                if (!pairSqr[j].isEmpty() && !sqrQueue.contains(j)) {
                    sqrQueue.add(j);

                }

                else if (!lineQueue.contains(j)) {
                    lineQueue.add(j);

                }

                countOfVertexWithCoord++;

            }
        }
    }

    void print() {
        for (int i = 0; i < N; i++) {
            System.out.println("Вершина " + (i + 1) + " имеет координаты: (" + getCoordX(i) + ", " + getCoordY(i) + ")");

        }

        System.out.println();

        for (Integer coords: coord) {
            System.out.print(coords + " ");

        }

        System.out.println();

    }

    public void addModelListener(GraphModelListener gr) {
        listenerList.add(gr);

    }

    private void graphInitialized() {
        for (GraphModelListener obj : listenerList) {
            obj.someChanges(matrix, coord);

        }

    }

    private int getCoordX(int vertex) {
        return coord[2 * vertex];

    }

    private int getCoordY(int vertex) {
        return coord[2 * vertex + 1];

    }

    private void setCoordX(int vertex, int x) {
        coord[2 * vertex] = x;

    }

    private void setCoordY(int vertex, int y) {
        coord[2 * vertex + 1] = y;

    }

}
