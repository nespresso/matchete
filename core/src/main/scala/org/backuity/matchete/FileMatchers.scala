/*
 * Copyright 2013 Bruno Bieth
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

package org.backuity.matchete

import java.io.File

trait FileMatchers extends MatcherSupport {

  def exist : Matcher[File] = matcher[File](
    description = "exist",
    validate = _.exists(),
    failureDescription = _.getCanonicalPath + " does not exist")

  def haveLastModified(time: Long) : Matcher[File] = matcher[File](
    description = "have last-modified " + time,
    validate = _.lastModified() == time,
    failureDescription = file => s"File ${file.getCanonicalPath} has not been last-modified at $time but at ${file.lastModified()}"
  )
}