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
package com.hasegawa.di.server.resources

import com.fasterxml.jackson.annotation.JsonProperty
import com.hasegawa.di.server.GCMControl
import com.hasegawa.di.server.daos.GCMDao
import com.hasegawa.di.server.utils.toUnixTimestamp
import io.dropwizard.validation.Validated
import org.joda.time.DateTime
import java.util.UUID
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.Consumes
import javax.ws.rs.OPTIONS
import javax.ws.rs.POST
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Context
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/gcm")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
class GCMResource(val gcmDao: GCMDao, val gcmControl: GCMControl) {

    @OPTIONS
    fun corsPreflight() = Response.ok().build()

    data class TokenPost(
            @JsonProperty var token: String? = null
    )

    @POST
    @Path("/tokens")
    fun postToken(@Context request: HttpServletRequest,
                  @Validated body: TokenPost): Response {
        if (body.token != null) {
            if (gcmControl.blockingValidateToken(body.token!!, request)) {
                gcmDao.insertToken(
                        UUID.randomUUID(),
                        body.token!!,
                        DateTime.now().toUnixTimestamp()
                )
                return Response.ok().build()
            }
        }
        return Response.notModified().build()
    }
}
