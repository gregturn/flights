/*
 * Copyright 2012-2014 the original author or authors.
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
package sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * After you launch the app, you can seek a bearer token like this:
 *
 * curl localhost:8080/oauth/token -d "grant_type=password&scope=read&username=greg&password=turnquist" -u foo:bar
 *
 * grant_type=password      (use as a password token)
 * scope=read               (read only scope)
 * username=greg            (username checked against SecurityConfiguration)
 * password=turnquist       (password checked against SecurityConfiguration)
 * -u foo:bar               (clientid:secret)
 *
 * Response should be similar to this:
 * {"access_token":"533de99b-5a0f-4175-8afd-1a64feb952d5","token_type":"bearer","expires_in":43199,"scope":"read"}
 *
 * With the token value, you can now interrogate the RESTful interface like this:
 * curl -H "Authorization: bearer [access_token]" localhost:8080/flights/1
 *
 * You should then see the pre-loaded data like this:
 * {
 *      "origin" : "Nashville",
 *      "destination" : "Dallas",
 *      "airline" : "Spring Ways",
 *      "flightNumber" : "OAUTH2",
 *      "date" : null,
 *      "traveler" : "Greg Turnquist",
 *      "_links" : {
 *          "self" : {
 *              "href" : "http://localhost:8080/flights/1"
 *          }
 *      }
 * }
 *
 * Test creating a new entry:
 *
 * curl -i -H "Authorization: bearer [access token]" -H "Content-Type:application/json" localhost:8080/flights -X POST -d @flight.json
 *
 * Insufficient scope? (read not write) Ask for a new token!
 * curl localhost:8080/oauth/token -d "grant_type=password&scope=write&username=greg&password=turnquist" -u foo:bar
 *
 * {"access_token":"cfa69736-e2aa-4ae7-abbb-3085acda560e","token_type":"bearer","expires_in":43200,"scope":"write"}
 *
 * Retry with the new token. There should be a Location header.
 *
 * Location: http://localhost:8080/flights/2
 *
 * curl -H "Authorization: bearer [access token]" localhost:8080/flights/2
 *
 */
@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	@Autowired
	private FlightRepository flightRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
