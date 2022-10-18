/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.rocketmq.eventbridge.tools.transform;

import java.util.List;
import org.apache.rocketmq.eventbridge.exception.EventBridgeException;

public class JsonPathTransform implements Transform {

    /**
     * The extact expr e.g:"{"id":"$.id","name":"$.data.name"}"
     */
    private Extract jsonPathExtract;

    public JsonPathTransform(Extract jsonPathExtract) {
        this.jsonPathExtract = jsonPathExtract;
    }

    @Override
    public Data process(Data inputData) throws EventBridgeException {
        List<Variable> variableList = extract(inputData, jsonPathExtract);
        if (variableList == null) {
            return new StringData();
        }
        if (variableList.size() != 1) {
            throw new EventBridgeException(TransformErrorCode.InvalidConfig);
        }

        if (variableList.get(0)
            .getValue() == null) {
            return new StringData();
        } else {
            return new StringData(variableList.get(0)
                .getValue()
                .toString());
        }
    }
}
