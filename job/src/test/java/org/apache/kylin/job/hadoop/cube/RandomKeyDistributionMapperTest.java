/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.kylin.job.hadoop.cube;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mrunit.mapreduce.MapDriver;
import org.apache.hadoop.mrunit.types.Pair;
import org.apache.kylin.job.constant.BatchConstants;
import org.apache.kylin.job.hadoop.invertedindex.RandomKeyDistributionMapper;
import org.junit.Before;
import org.junit.Test;

/**
 * @author ysong1
 * 
 */
public class RandomKeyDistributionMapperTest {

    MapDriver<Text, Text, Text, LongWritable> mapDriver;

    @Before
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public void setUp() {
        RandomKeyDistributionMapper mapper = new RandomKeyDistributionMapper();
        mapDriver = MapDriver.newMapDriver(mapper);
    }

    @Test
    public void test() throws IOException {
        List<Text> data = new ArrayList<Text>();
        for (int i = 0; i < 1001; i++) {
            data.add(new Text(String.valueOf(i)));
        }

        for (Text t : data) {
            mapDriver.addInput(t, new Text("abc"));
        }

        mapDriver.getConfiguration().set(BatchConstants.MAPPER_SAMPLE_NUMBER, "100");
        List<Pair<Text, LongWritable>> result = mapDriver.run();
        assertEquals(100, result.size());

        for (Pair<Text, LongWritable> p : result) {
            System.out.println(p.getFirst());
        }
    }

}
