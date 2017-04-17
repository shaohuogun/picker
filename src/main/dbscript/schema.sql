--
--    Copyright 2015-2016 the original author or authors.
--
--    Licensed under the Apache License, Version 2.0 (the "License");
--    you may not use this file except in compliance with the License.
--    You may obtain a copy of the License at
--
--       http://www.apache.org/licenses/LICENSE-2.0
--
--    Unless required by applicable law or agreed to in writing, software
--    distributed under the License is distributed on an "AS IS" BASIS,
--    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
--    See the License for the specific language governing permissions and
--    limitations under the License.
--

drop table if exists `PICKER_STRATEGY`;
drop table if exists `PICKER_REQUEST`;
drop table if exists `PICKER_RESULT`;

CREATE TABLE `PICKER_STRATEGY` (
  `id` char(36) NOT NULL,
  `creator` char(36) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modifier` char(36) DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `deleted` char(1) NOT NULL,
  `url_regex` varchar(256) NOT NULL,
  `name` varchar(128) NOT NULL,
  `xml` blob NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `PICKER_REQUEST` (
  `id` char(36) NOT NULL,
  `creator` char(36) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modifier` char(36) DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `deleted` char(1) NOT NULL,
  `target_url` varchar(256) NOT NULL,
  `target_type` varchar(32) NOT NULL,
  `batch_no` char(36) DEFAULT NULL,
  `result_id` char(36) DEFAULT NULL,
  `status` varchar(16) NOT NULL,
  `start_time` datetime DEFAULT NULL,
  `end_time` datetime DEFAULT NULL, 
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `PICKER_RESULT` (
  `id` char(36) NOT NULL,
  `creator` char(36) NOT NULL,
  `create_date` datetime NOT NULL,
  `last_modifier` char(36) DEFAULT NULL,
  `last_modify_date` datetime DEFAULT NULL,
  `deleted` char(1) NOT NULL,
  `request_id` char(36) DEFAULT NULL,
  `strategy_id` char(36) NOT NULL,  
  `json` blob NOT NULL,
  `sent` char(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;





