package Algorithm.AlgorithmFourthEdition.UnionFind;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

/**
 * Created by hfc on 2020/3/1.
 */
public class RandomGridAnimation extends Application {

    GraphicsContext gc;
    StackPane pane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        pane = new StackPane();

        Canvas c = new Canvas(520, 520);
        gc = c.getGraphicsContext2D();
        gc.setFill(Color.color(0.9, 0.9, 0.9));
        gc.fillRect(0d, 0d, 520d, 520d);
        pane.getChildren().add(c);

        Button btn = new Button();
        btn.setText("开始");
        btn.setOnAction(event -> drawGridPoint());
        pane.getChildren().add(btn);

        Scene scene = new Scene(pane, 600, 600);
        primaryStage.setTitle("网格图");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void drawGridPoint() {
        // 去掉btn
        pane.getChildren().remove(1);

        // 网格框的横纵点数
        int X = 20;
        int Y = 20;
        // 点距
        double xInc = 500d / (X-1);
        double yInc = 500d / (Y-1);
        gc.setFill(Color.RED);
        for (int i=0; i<Y; i++) {
            for (int j=0; j<X; j++) {
                double x = j * xInc + 10;
                double y = i * yInc + 10;
                // -1.5d是为了调整圆心
                gc.fillOval(x - 1.5d, y - 1.5d, 3d, 3d);
            }
        }

        RandomGrid.Connection[] conns = RandomGrid.generate(X, Y);
        Thread t = new Thread(() -> {
            for (RandomGrid.Connection conn : conns) {
                double px = (conn.p % X) * xInc + 10d;
                double py = (double) (conn.p / X) * yInc + 10d;
                double qx = (conn.q % X) * xInc + 10d;
                double qy = (double) (conn.q / X) * yInc + 10d;

                gc.strokeLine(px, py, qx, qy);
                try {
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
