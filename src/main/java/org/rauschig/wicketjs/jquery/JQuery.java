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

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.rauschig.wicketjs.IJsExpression;
import org.rauschig.wicketjs.IJsStatement;
import org.rauschig.wicketjs.JsCall;
import org.rauschig.wicketjs.JsExpression;
import org.rauschig.wicketjs.JsExpressionStatement;
import org.rauschig.wicketjs.JsFunction;
import org.rauschig.wicketjs.JsIdentifier;
import org.rauschig.wicketjs.JsLiteral;
import org.rauschig.wicketjs.behavior.AbstractJsBehavior;
import org.rauschig.wicketjs.compiler.JsCompiler;
import org.rauschig.wicketjs.compiler.JsJoiner;
import org.rauschig.wicketjs.util.Strings;

/**
 * JQuery
 */
public class JQuery extends JsExpression {

    private static final long serialVersionUID = -3713464209858405030L;

    private IJsExpression selector;

    private List<IJsExpression> chainedExpressions = new ArrayList<>();

    public JQuery() {
        super("");
    }

    public JQuery(Component component) {
        this("#" + component.getMarkupId());
    }

    public JQuery(AbstractJsBehavior behavior) {
        this("#" + behavior.id());
    }

    public JQuery(String selector) {
        this(new JsLiteral.JsString(selector));
    }

    public JQuery(IJsExpression selector) {
        super("");
        this.selector = selector;
    }

    public static JQuery jQuery() {
        return new JQuery();
    }

    public static JQuery jQuery(Component component) {
        return new JQuery(component);
    }

    private static JQuery jQuery(AbstractJsBehavior behavior) {
        return new JQuery(behavior);
    }

    public static JQuery jQuery(String selector) {
        return new JQuery(selector);
    }

    public static JQuery jQuery(IJsExpression selector) {
        return new JQuery(selector);
    }

    public static JQuery $() {
        return jQuery();
    }

    public static JQuery $(Component component) {
        return jQuery(component);
    }

    public static JQuery $(AbstractJsBehavior behavior) {
        return jQuery(behavior);
    }

    public static JQuery $(String selector) {
        return jQuery(selector);
    }

    public static JQuery $(IJsExpression selector) {
        return jQuery(selector);
    }

    /**
     * Shorthand for {@link #chain(org.rauschig.wicketjs.JsCall)};
     * 
     * @param call the function call to chain
     * @return
     */
    public JQuery _(JsCall call) {
        return chain(call);
    }

    public JQuery _(JsIdentifier identifier) {
        return chain(identifier);
    }

    public JQuery _(String identifier) {
        return chain(identifier);
    }

    public JQuery chain(JsCall call) {
        return chain0(call);
    }

    public JQuery chain(JsIdentifier identifier) {
        return chain0(identifier);
    }

    public JQuery chain(String identifier) {
        return chain(new JsIdentifier(identifier));
    }

    protected JQuery chain0(IJsExpression expression) {
        chainedExpressions.add(expression);
        return this;
    }

    public JQuery call(String function) {
        return chain(new JsCall(function));
    }

    public JQuery call(String function, Object... parameters) {
        return chain(new JsCall(function, parameters));
    }

    public JQuery bind(String event, String callbackBody) {
        return chain(new JQueryBind(event, callbackBody));
    }

    public JQuery bind(String event, IJsExpression callbackBody) {
        return chain(new JQueryBind(event, callbackBody));
    }

    public JQuery bind(String event, JsIdentifier callback) {
        return chain(new JQueryBind(event, callback));
    }

    public JQuery bind(String event, JsFunction callback) {
        return chain(new JQueryBind(event, callback));
    }

    public JQuery click(String callbackBody) {
        return bind("click", callbackBody);
    }

    public JQuery click(IJsExpression callbackBody) {
        return bind("click", callbackBody);
    }

    public JQuery click(JsIdentifier callback) {
        return bind("click", callback);
    }

    public JQuery click(JsFunction callback) {
        return bind("click", callback);
    }

    public JQuery each(JsFunction callback) {
        return call("each", callback);
    }

    public JQuery each(String callbackBody) {
        return each(new JsFunction(callbackBody, "index", "element"));
    }

    public JQuery each(IJsExpression callbackBody) {
        return each(new JsFunction(callbackBody, "index", "element"));
    }

    public JQuery find(String selector) {
        return call("find", selector);
    }

    public JQuery parent() {
        return call("parent");
    }

    public JQuery trigger(String event) {
        return call("trigger", event);
    }

    public JQuery parent(String selector) {
        return call("parent", selector);
    }

    public JQuery addClass(String cssClass) {
        return call("addClass", cssClass);
    }

    public JQuery removeClass(String cssClass) {
        return call("removeClass", cssClass);
    }

    public JQuery toggleClass(String cssClass) {
        return call("toggleClass", cssClass);
    }

    public JQuery addClass(String... cssClass) {
        return call("addClass", Strings.join(cssClass, " "));
    }

    public JQuery removeClass(String... cssClass) {
        return call("removeClass", Strings.join(cssClass, " "));
    }

    public JQuery toggleClass(String... cssClass) {
        return call("toggleClass", Strings.join(cssClass, " "));
    }

    public String js() {
        StringBuilder js = new StringBuilder();

        js.append("$");
        if (selector != null) {
            js.append("(").append(new JsCompiler(selector).compile()).append(")");
        }

        if (chainedExpressions != null && !chainedExpressions.isEmpty()) {
            js.append(".");
            js.append(new JsJoiner<>(chainedExpressions, ".").compile());
        }

        return js.toString();
    }

    @Override
    public CharSequence getExpression() {
        return js();
    }

    public IJsStatement terminate() {
        return asStatement();
    }

    public IJsStatement asStatement() {
        return new JsExpressionStatement(this);
    }
}
