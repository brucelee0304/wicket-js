/**
 *    Copyright 2014 Thomas Rausch
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.rauschig.wicketjs.jquery;

import org.rauschig.wicketjs.IJavaScript;
import org.rauschig.wicketjs.JsCall;
import org.rauschig.wicketjs.JsFunction;
import org.rauschig.wicketjs.JsIdentifier;

/**
 * JQueryBind
 */
public class JQueryBind extends JsCall {

    private static final long serialVersionUID = 2699505604920707203L;

    public static final JsIdentifier EVENT_OBJECT = new JsIdentifier("eventObject");

    public JQueryBind(String event, String callbackBody) {
        this(event, new JsFunction(callbackBody));
    }

    public JQueryBind(String event, IJavaScript callbackBody) {
        this(event, new JsFunction(callbackBody));
    }

    public JQueryBind(String event, JsIdentifier callback) {
        super("bind", event, callback);
    }

    public JQueryBind(String event, JsFunction callback) {
        super("bind", event, callback);

        if (!callback.getParameters().contains(EVENT_OBJECT)) {
            callback.addParameter(EVENT_OBJECT);
        }
    }

}
