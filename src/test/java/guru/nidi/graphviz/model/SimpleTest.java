/*
 * Copyright © 2015 Stefan Niederhauser (nidin@gmx.ch)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package guru.nidi.graphviz.model;

import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.engine.GraphvizException;
import guru.nidi.graphviz.engine.GraphvizJdkEngine;
import guru.nidi.graphviz.engine.GraphvizV8Engine;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static guru.nidi.graphviz.engine.Format.SVG;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.*;

public class SimpleTest {
    @BeforeClass
    public static void init() {
        Graphviz.useEngine(new GraphvizV8Engine(), new GraphvizJdkEngine());
    }

    @AfterClass
    public static void end() {
        Graphviz.releaseEngine();
    }

    @Test
    public void simple() {
        final Graphviz viz = Graphviz.fromString("digraph g { \"a\\b'c\" -> b; }");
        assertNotNull(viz.render(SVG).toString());
    }

    @Test
    public void dotError() {
        try {
            System.out.println("Try error...");
            Graphviz.fromString("g { a -> b; }").render(SVG).toString();
            fail();
        } catch (GraphvizException e) {
            assertThat(e.getMessage(), containsString("syntax error in line 1 near 'g'"));
        }
    }
}
