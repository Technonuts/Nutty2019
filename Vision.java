package frc.robot;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Vision extends Thread {

    UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    CvSink cvSink = CameraServer.getInstance().getVideo();
    
    Mat frame = new Mat();
    GripPipeline pipeline = new GripPipeline();
    double targetCenter = 0;
    double leftCenter;
    double rightCenter;

    boolean haveTargets = false;

    double error;
    double turn;

    static final int FRAME_WIDTH = 320;
    static final int FRAME_HEIGHT = 240;
    static final int FRAME_WIDTH_CENTER = FRAME_WIDTH/2;
    static final int FRAME_HEIGHT_CENTER = FRAME_HEIGHT/2;

    double nudge = 0;

    public void run(){
        camera.setResolution(FRAME_WIDTH, FRAME_HEIGHT);
        camera.setExposureManual(20);

        while(true) {
            try {
                cvSink.grabFrame(frame);
                pipeline.process(frame);

                if (pipeline.filterContoursOutput().size() == 2) {
                    Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
                    leftCenter = r.x + (r.width/2);
                    r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(1));
                    rightCenter = r.x + (r.width/2);
                    targetCenter = (leftCenter+rightCenter)/2;
                    haveTargets = true;

                    SmartDashboard.putNumber("Left Center X", leftCenter);
                    SmartDashboard.putNumber("Right Center X", rightCenter);
                    SmartDashboard.putNumber("Target Center X", targetCenter);
 
                }
                else {
                    haveTargets = false;
                }
                SmartDashboard.putBoolean("2 Targets Found", haveTargets);
                SmartDashboard.putNumber("Number of Targets", pipeline.filterContoursOutput().size());
            }
            catch(Exception e) {
                System.out.println(e);
            }
            
        }
    }

    public void visionDrive() 
    {
        //If two targets are found, center on them then drive straight.
        if(pipeline.filterContoursOutput().size() == 2) {
            error = targetCenter - FRAME_WIDTH_CENTER;

            //Increase the number FRAME_WIDTH is divided by to increase turn rate. 4 is good.
            turn = error/(FRAME_WIDTH/4);

            //Turn speed will never be more than half speed in either direction.
            /*if (turn < -.5) {
                turn = -.5;
            }
            else if (turn > .5) {
                turn = .5;
            }*/

            //If centered, drive straight. Centers along the way just in case.
            if (error < 20 && error > -20) {
                Drive.arcadeDrive(.35, turn);
                nudge = 0; 
                
            }
            else {
                //Increases amount to nudge the longer the robot is off center.
                if (turn < 0) {
                    nudge -= .001;
                }
                else if (turn > 0) {
                    nudge += .001;
                }

                //Center on the targets, plus a little nudge.
                Drive.arcadeDrive(0, turn + nudge);   
            }
        }
        else {
            Drive.arcadeDrive(0, 0);
        }
    }

}