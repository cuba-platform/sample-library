/*
 * Copyright (c) 2017 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.company.library.web.bookpublication;

import com.haulmont.cuba.gui.components.Formatter;

/**
 * This formatter is needed because the default format for {@link com.haulmont.chile.core.datatypes.impl.IntegerDatatype}
 * contains grouping separator. We could redefine the whole <code>datatypes.xml</code> for the project, but we
 * chose to create this custom formatter and use it in UI components explicitly.
 */
public class YearFormatter implements Formatter<Integer> {

    @Override
    public String format(Integer value) {
        return value == null ? "" : String.valueOf(value);
    }
}
