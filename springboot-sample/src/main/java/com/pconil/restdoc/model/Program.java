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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pconil.restdoc.annotation.AsciidocAnnotation;
import com.pconil.restdoc.annotation.InspectToDocument;
import io.swagger.annotations.ApiModelProperty;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Object that 'summarize' a Program (light data).
 */
@InspectToDocument(description = "Electronic Program Guide Item")
public class Program {

    /**
     * Program identifier.
     */
    @AsciidocAnnotation(description = "Program identifier")
    @ApiModelProperty(required = true, value = "Program identifier")
    @JsonProperty("id")
    private String id = null;

    /**
     * Program type.
     */
    @AsciidocAnnotation(description = "Program type")
    @ApiModelProperty(required = true, value = "Program type")
    @JsonProperty("programType")
    private String programType = null;

    /**
     * Program title.
     */
    @AsciidocAnnotation(description = "Program title")
    @ApiModelProperty(required = true, value = "Program title")
    @JsonProperty("title")
    private String title = null;

    /**
     * Id of channel broadcasting this program.
     */
    @AsciidocAnnotation(description = "Id of channel broadcasting this program")
    @ApiModelProperty(required = true, value = "Identifier of the channel where the program is broadcasted")
    @JsonProperty("channelId")
    private String channelId = null;

    /**
     * Diffusion date in seconds.
     */
    @AsciidocAnnotation(description = "Diffusion date in seconds")
    @ApiModelProperty(required = true, value = "Diffusion date in seconds")
    @JsonProperty("diffusionDate")
    private Long diffusionDate = null;

    /**
     * Program duration in second.
     */
    @AsciidocAnnotation(description = "Program duration in second")
    @ApiModelProperty(required = true, value = "Duration in seconds")
    @JsonProperty("duration")
    private Long duration = null;

    /**
     * Program CSA level.
     */
    @AsciidocAnnotation(description = "Program CSA level")
    @ApiModelProperty(required = true, value = "Audience maturity level")
    @JsonProperty("csa")
    private Long csa = null; 

    /**
     * Kind of program (ex: variety).
     */
    @AsciidocAnnotation(description = "Kind of program (ex: comedy)")
    @ApiModelProperty(required = true, value = "Program kind (e.g Comedy)")
    @JsonProperty("kind")
    private String kind = null;

    /**
     * More detailed kind (e.g Comedy-drama).
     */
    @AsciidocAnnotation(description = "More detailed kind (e.g Comedy-drama)")
    @ApiModelProperty(required = true, value = "More detailed kind (e.g Comedy-drama)")
    @JsonProperty("kindDetailed")
    private String kindDetailed = null;

    /**
     * Program sysnopsis.
     */
    @AsciidocAnnotation(description = "Program sysnopsis")
    @ApiModelProperty(required = true, value = "Program synopsis")
    @JsonProperty("synopsis")
    private String synopsis = null;

    /**
     * Program language.
     */
    @AsciidocAnnotation(description = "Program language")
    @ApiModelProperty(required = true, value = "Program language")
    @JsonProperty("languageVersion")
    private String languageVersion = null;

    /**
     * Indicates if the program has some hearing-impaired subtitle.
     */
    @AsciidocAnnotation(description = "Indicates if the program has some hearing-impaired subtitle")
    @ApiModelProperty(required = true, value = "Indicates if the program has some hearing-impaired subtitle")
    @JsonProperty("hearingImpaired")
    private Boolean hearingImpaired = null;

    /**
     * Indicates if the program has an audio description track.
     */
    @ApiModelProperty(required = true, value = "Indicates if the program has an audio description track")
    @JsonProperty("audioDescription")
    @AsciidocAnnotation(description = "Indicates if the program has an audio description track")
    private Boolean audioDescription = null;

    /**
     * If the type of the program is EPISODE, gives the season number.
     */
    @AsciidocAnnotation(description = "If the type of the program is EPISODE, gives the season number")
    @ApiModelProperty(required = true, value = "If the type of the program is EPISODE, gives the season number")
    @JsonProperty("season")
    private Long season = null;

    /**
     * If the type of the program is EPISODE, gives the position of the episode in the season.
     */
    @AsciidocAnnotation(description = "If the type of the program is EPISODE, gives the position of the episode in "
                                      + "the season")
    @ApiModelProperty(required = true, value = "if the type of the program is EPISODE, gives the position of the episode"
                                               + "in the season")
    @JsonProperty("episodeNumber")
    private String episodeNumber = null;

    /**
     * Program definition.
     */
    @AsciidocAnnotation(description = "Program definition")
    @ApiModelProperty(required = true, value = "Program definition", allowableValues = "SD, HD")
    @JsonProperty("definition")
    private String definition = null;

    /**
     * List of links which points to entity in relation with the current program.
     */
    @AsciidocAnnotation(description = "List of <<com.pconil.restdoc.model-Link, links>> which points to entity in relation with"
                                      + " the current program")
    @ApiModelProperty(required = true, value = "List of links which points to entity in relation with the current program")
    @JsonProperty("links")
    private List<URI> links = new ArrayList<>();

    /**
     * Part of day when program is broadcast(ed).
     */
    @AsciidocAnnotation(description = "Part of day when program start")
    @ApiModelProperty(required = true, value = "Part of day when program start, PT for prime time",
            allowableValues = "PT1, PT2, PT3, OTHER")
    @JsonProperty("dayPart")
    private String dayPart = null;

    /**
     * Identifier of the catchup program related to this live program.
     */
    @AsciidocAnnotation(description = "Identifier of the catchup program related to this live program")
    @ApiModelProperty(required = true, value = "Identifier of the catchup program related to this live program")
    @JsonProperty("catchupId")
    private String catchupId = null;


    //<editor-fold desc="Constructors">
    /**
     * Full param constructor.
     */
    @SuppressWarnings("all")
    @JsonIgnore
    public Program(String id, String programType, String title, String channelId,
                        Long diffusionDate, Long duration, Long csa, String kind, String kindDetailed,
                        String synopsis,
                        String languageVersion, Boolean hearingImpaired, Boolean audioDescription,
                        Long season,
                        String episodeNumber, String definition, List<URI> links,
                        String dayPart,
                        String catchupId) {
        this.id = id;
        this.programType = programType;
        this.title = title;
        this.channelId = channelId;
        this.diffusionDate = diffusionDate;
        this.duration = duration;
        this.csa = csa;
        this.kind = kind;
        this.kindDetailed = kindDetailed;
        this.synopsis = synopsis;
        this.languageVersion = languageVersion;
        this.hearingImpaired = hearingImpaired;
        this.audioDescription = audioDescription;
        this.season = season;
        this.episodeNumber = episodeNumber;
        this.definition = definition;
        this.links = links;
        this.dayPart = dayPart;
        this.catchupId = catchupId;
    }

    /**
     * Default constructor.
     */
    public Program() {

    }
    //</editor-fold>

    //<editor-fold desc="Getters, setters, equals ...">

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
    
    public Long getDiffusionDate() {
        return diffusionDate;
    }

    public void setDiffusionDate(Long diffusionDate) {
        this.diffusionDate = diffusionDate;
    }
    
    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }
    
    public Long getCsa() {
        return csa;
    }

    public void setCsa(Long csa) {
        this.csa = csa;
    }
    
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }
    
    public String getKindDetailed() {
        return kindDetailed;
    }

    public void setKindDetailed(String kindDetailed) {
        this.kindDetailed = kindDetailed;
    }
    
    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    
    public String getLanguageVersion() {
        return languageVersion;
    }

    public void setLanguageVersion(String languageVersion) {
        this.languageVersion = languageVersion;
    }
    
    public Boolean getHearingImpaired() {
        return hearingImpaired;
    }

    public void setHearingImpaired(Boolean hearingImpaired) {
        this.hearingImpaired = hearingImpaired;
    }
    
    public Boolean getAudioDescription() {
        return audioDescription;
    }

    public void setAudioDescription(Boolean audioDescription) {
        this.audioDescription = audioDescription;
    }
    
    public Long getSeason() {
        return season;
    }

    public void setSeason(Long season) {
        this.season = season;
    }
    
    public String getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(String episodeNumber) {
        this.episodeNumber = episodeNumber;
    }
    
    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public List<URI> getLinks() {
        return links;
    }

    public void setLinks(List<URI> links) {
        this.links = links;
    }

    public String getDayPart() {
        return dayPart;
    }

    public void setDayPart(String dayPart) {
        this.dayPart = dayPart;
    }

    public String getCatchupId() {
        return catchupId;
    }

    public void setCatchupId(String catchupId) {
        this.catchupId = catchupId;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Program programLight = (Program) o;
        return Objects.equals(id, programLight.id)
               && Objects.equals(programType, programLight.programType)
               && Objects.equals(title, programLight.title)
               && Objects.equals(channelId, programLight.channelId)
               && Objects.equals(diffusionDate, programLight.diffusionDate)
               && Objects.equals(duration, programLight.duration)
               && Objects.equals(csa, programLight.csa)
               && Objects.equals(kind, programLight.kind)
               && Objects.equals(kindDetailed, programLight.kindDetailed)
               && Objects.equals(synopsis, programLight.synopsis)
               && Objects.equals(languageVersion, programLight.languageVersion)
               && Objects.equals(hearingImpaired, programLight.hearingImpaired)
               && Objects.equals(audioDescription, programLight.audioDescription)
               && Objects.equals(season, programLight.season)
               && Objects.equals(episodeNumber, programLight.episodeNumber)
               && Objects.equals(definition, programLight.definition)
               && Objects.equals(links, programLight.links)
               && Objects.equals(dayPart, programLight.dayPart)
               && Objects.equals(catchupId, programLight.catchupId);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "class ProgramLight {\n"
               + "  id: " + id + "\n"
               + "  programType: " + programType + "\n"
               + "  title: " + title + "\n"
               + "  channelId: " + channelId + "\n"
               + "  diffusionDate: " + diffusionDate + "\n"
               + "  duration: " + duration + "\n"
               + "  csa: " + csa + "\n"
               + "  kind: " + kind + "\n"
               + "  kindDetailed: " + kindDetailed + "\n"
               + "  synopsis: " + synopsis + "\n" 
               + "  languageVersion: " + languageVersion + "\n"
               + "  hearingImpaired: " + hearingImpaired + "\n"
               + "  audioDescription: " + audioDescription + "\n"
               + "  season: " + season + "\n"
               + "  episodeNumber: " + episodeNumber + "\n"
               + "  definition: " + definition + "\n"
               + "  links: " + links + "\n"
               + "  dayPart: " + dayPart + "\n"
               + "  catchupId: " + catchupId + "\n"
               + "}\n";
    }
    //</editor-fold>
}


