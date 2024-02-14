/*
 * Copyright 2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package ai.djl.examples.inference;

import ai.djl.ModelException;
import ai.djl.examples.inference.face.RetinaFaceDetection;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.testing.TestRequirements;
import ai.djl.translate.TranslateException;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class RetinaFaceDetectionTest {

    @Test
    public void testRetinaFaceDetection() throws ModelException, TranslateException, IOException {
        TestRequirements.engine("PyTorch");
        TestRequirements.nightly();
        DetectedObjects result = RetinaFaceDetection.predict();

        List<String> objects = Collections.singletonList("Face");
        for (Classifications.Classification obj : result.items()) {
            Assert.assertTrue(objects.contains(obj.getClassName()));
            Assert.assertTrue(Double.compare(obj.getProbability(), 0.6) > 0);
        }
    }
}
