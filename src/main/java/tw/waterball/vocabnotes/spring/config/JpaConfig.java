/*
 *  Copyright 2020 johnny850807 (Waterball)
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

package tw.waterball.vocabnotes.spring.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import tw.waterball.vocabnotes.models.repositories.RepositoryComponentScan;
import tw.waterball.vocabnotes.spring.profiles.Prod;

import javax.sql.DataSource;
import java.util.ResourceBundle;


/**
 * @author johnny850807@gmail.com (Waterball))
 */
@EnableJpaRepositories
@Configuration
@ComponentScan(basePackageClasses = RepositoryComponentScan.class)
public class JpaConfig {

    @Bean
    @Prod
    public DataSource mysql() {
        ResourceBundle bundle = ResourceBundle.getBundle("datasource");
        return DataSourceBuilder.create()
                .driverClassName(bundle.getString("driverClassName"))
                .url(bundle.getString("url"))
                .username(bundle.getString("username"))
                .password(bundle.getString("password"))
                .build();
    }


}
