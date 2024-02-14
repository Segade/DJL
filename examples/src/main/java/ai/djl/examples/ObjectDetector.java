/**
 * Class that runs the modle Zoo for the object detection
 * Author: Deep Java Library
 */

package ai.djl.examples;

// import the necesssary libraries for the class

import ai.djl.Application;
import ai.djl.ModelException;
import ai.djl.engine.Engine;
import ai.djl.examples.inference.ObjectDetection;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.training.util.ProgressBar;
import ai.djl.translate.TranslateException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class ObjectDetector {
    /**
     * method that performs the detection of objects in an image
     * by using the model Zoo
     * It configures the TensorFlow, engine and backbone used for the process
     * It receives the image to be processed
     * It returns a DetectedObject with the dedata of the detection
     *
     * @param img
     * @return
     * @throws IOException
     * @throws ModelException
     * @throws TranslateException
     */

    public static DetectedObjects predict(Image img) throws IOException, ModelException, TranslateException {

        String backbone;
        if ("TensorFlow".equals(Engine.getDefaultEngineName())) {
            backbone = "mobilenet_v2";
        } else {
            backbone = "resnet50";
        }

        Criteria<Image, DetectedObjects> criteria =
                Criteria.builder()
                        .optApplication(Application.CV.OBJECT_DETECTION)
                        .setTypes(Image.class, DetectedObjects.class)
                        .optFilter("backbone", backbone)
                        .optEngine(Engine.getDefaultEngineName())
                        .optProgress(new ProgressBar())
                        .build();

        try (ZooModel<Image, DetectedObjects> model = criteria.loadModel()) {
            try (Predictor<Image, DetectedObjects> predictor = model.newPredictor()) {
                DetectedObjects detection = predictor.predict(img);

                return detection;
            }
        }
    } // end of predict


} // end class
