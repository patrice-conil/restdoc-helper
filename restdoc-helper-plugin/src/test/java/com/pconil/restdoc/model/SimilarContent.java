/*
 * Copyright 2016-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pconil.restdoc.model;

import com.pconil.restdoc.annotation.AsciidocAnnotation;
import com.pconil.restdoc.annotation.MustBeDocumented;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * SimilarContent describes a similar content as the one it refers to.
 */
@MustBeDocumented(description = "SimilarContent describes a similar content as the one it refers to")
class SimilarContent {

    /**
     * Title.
     **/
    @AsciidocAnnotation(description = "Title")
    private String title = null;

    /**
     * Content identifier : the content identifier depends on its contentType. For MOVIE contentType, id is a videoId
     * (aka externalAssetId in RTV). For SEASON contentType, id is a seasonId (aka serieId in RTV).
     **/
    @AsciidocAnnotation(description = "Content identifier : the content identifier depends on its contentType. "
            + "For MOVIE contentType, id is a videoId (aka externalAssetId in RTV). For SEASON contentType, id is " 
            + "a seasonId (aka serieId in RTV)")
    private String id = null;

    /**
     * Offer identifier.
     **/
    @AsciidocAnnotation(description = "Offer identifier")
    private String offerId = null;

    /**
     * Type of com.pconil.restdoc.service.
     */
    @AsciidocAnnotation(description = "Type of com.pconil.restdoc.service see <<com.pconil.restdoc.model-ServiceTypeEnum>>")
    private String serviceType = null;

    /**
     * Type of content.
     */
    @AsciidocAnnotation(description = "Type of content see <<com.pconil.restdoc.model-ContentTypeEnum>>")
    private String contentType = null;

    /**
     * Type of similarity.
     */
    @AsciidocAnnotation(description = "Type of similarity see <<com.pconil.restdoc.model-SimilarityEnum>>")
    private String  similarity = null;

    /**
     * CSA Level between 1 and 5.
     **/
    @AsciidocAnnotation(description = "CSA Level", constraints = "between 1 and 5")
    private Integer csaLevel = null;

    /**
     * List of available images.
     **/
    @AsciidocAnnotation(description = "Available images see <<com.pconil.restdoc.model-Image>>")
    private List<String> images = new ArrayList<>();
    


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SimilarContent similarContent = (SimilarContent) o;
        return Objects.equals(title, similarContent.title)
                && Objects.equals(id, similarContent.id)
                && Objects.equals(offerId, similarContent.offerId)
                && Objects.equals(serviceType, similarContent.serviceType)
                && Objects.equals(contentType, similarContent.contentType)
                && Objects.equals(similarity, similarContent.similarity)
                && Objects.equals(csaLevel, similarContent.csaLevel)
                && Objects.equals(images, similarContent.images);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, id, offerId, serviceType, contentType, similarity, csaLevel, images);
    }

    @Override
    public String toString() {

        return "class SimilarContent {\n" 
                + "  title: " + title + "\n" 
                + "  id: " + id + "\n" 
                + "  offerId: " + offerId + "\n" 
                + "  serviceType: " + serviceType + "\n" 
                + "  contentType: " + contentType + "\n" 
                + "  similarity: " + similarity + "\n" 
                + "  csaLevel: " + csaLevel + "\n" 
                + "  images: " + images + "\n" 
                + "}\n";
    }
    //</editor-fold>
}
