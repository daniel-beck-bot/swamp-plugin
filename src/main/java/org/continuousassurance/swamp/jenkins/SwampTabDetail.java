//  SwampTabDetail.java
//
//  Copyright 2016 Jared Sweetland, Vamshi Basupalli, James A. Kupsch
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
package org.continuousassurance.swamp.jenkins;

import hudson.model.Run;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.views.DetailFactory;
import hudson.plugins.analysis.views.TabDetail;

import java.util.Collection;

/**
 * Detail view for the FindBugs plug-in: uses different table visualization.
 *
 * @author Ulli Hafner
 */

public class SwampTabDetail extends TabDetail {
    private static final long serialVersionUID = -3117538321276802327L;

    /**
     * Creates a new instance of {@link SwampTabDetail}.
     *
     * @param owner
     *            current build as owner of this action.
     * @param detailFactory
     *            the detail factory to use
     * @param annotations
     *            the module to show the details for
     * @param url
     *            URL to render the content of this tab
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */

    public SwampTabDetail(final Run<?, ?> owner, final DetailFactory detailFactory, final Collection<FileAnnotation> annotations, final String url, final String defaultEncoding) {
        super(owner, detailFactory, annotations, url, defaultEncoding);
    }

    @Override
    public String getWarnings() {
        return "swamp-warnings.jelly";
    }
}