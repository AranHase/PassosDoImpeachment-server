/*******************************************************************************
 * Copyright 2016 Allan Yoshio Hasegawa
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
 ******************************************************************************/
package com.hasegawa.di.server

import com.fasterxml.jackson.annotation.JsonProperty
import io.dropwizard.Configuration
import io.dropwizard.client.JerseyClientConfiguration
import io.dropwizard.db.DataSourceFactory
import javax.validation.Valid
import javax.validation.constraints.NotNull

class ServerConfiguration : Configuration() {
    @JsonProperty
    var gcmKey: String? = null

    @JsonProperty
    var gcmInvalidTokensBeforeBan: Int? = null

    @JsonProperty
    var gcmTimeSpanToStoreInvalidTokens: Long? = null

    @JsonProperty
    var gcmTempBanCsfTimeInSeconds: Long? = null

    @Valid
    @NotNull
    @JsonProperty("database")
    var dataSourceFactory = DataSourceFactory()

    @Valid
    @NotNull
    private var jerseyClient = JerseyClientConfiguration()

    @JsonProperty("jerseyClient")
    fun getJerseyClientConfiguration(): JerseyClientConfiguration {
        return jerseyClient
    }

    @JsonProperty("jerseyClient")
    fun setHttpClientConfiguration(jerseyClient: JerseyClientConfiguration) {
        this.jerseyClient = jerseyClient
    }
}
