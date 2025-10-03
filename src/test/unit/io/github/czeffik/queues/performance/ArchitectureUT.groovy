package io.github.czeffik.queues.performance

import com.tngtech.archunit.core.domain.JavaClasses
import com.tngtech.archunit.core.importer.ClassFileImporter
import com.tngtech.archunit.core.importer.ImportOption
import com.tngtech.archunit.lang.Priority
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RestController
import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.priority

class ArchitectureUT extends Specification {
    static final String BASE_PACKAGE = 'io.github.czeffik.queues.performance'
    static final String DOMAIN_PACKAGE = "${BASE_PACKAGE}.domain"
    static final String INFRASTRUCTURE_PACKAGE = "${BASE_PACKAGE}.infrastructure"
    static final String INTERFACES_PACKAGE = "${BASE_PACKAGE}.interfaces"
    static final String[] DOMAINS = [
        'common'
    ]
    static final String[] PACKAGES_ALLOWED_IN_DOMAIN = [
        'org.slf4j',
        'lombok',
        'com.google.common',
        'java'
    ]

    static final String[] PACKAGES_ALLOWED_IN_DOMAIN_MATCHER = PACKAGES_ALLOWED_IN_DOMAIN.collect { "${it}.." }

    @Shared
    ClassFileImporter importer = new ClassFileImporter().withImportOption(new ImportOption.DoNotIncludeTests())
    @Shared
    JavaClasses allClasses = importer.importPackages(BASE_PACKAGE).as('ALL')
    @Shared
    JavaClasses domainClasses = importer.importPackages(DOMAIN_PACKAGE).as('DOMAIN')

    @Unroll
    def 'domain should use classes from allowed packages and #DOMAIN package'() {
        given:
            def domainPackage = "${DOMAIN_PACKAGE}.${DOMAIN}.."
        and:
            def rule = priority(Priority.HIGH).noClasses()
                .that().resideInAPackage(domainPackage)
                .should().dependOnClassesThat().resideOutsideOfPackages(getAllowedPackages(domainPackage))
                .allowEmptyShould(true)
        expect:
            rule.check(domainClasses)
        where:
            DOMAIN << DOMAINS
    }

    @Ignore
    def 'all fields should be private'() {
        given:
            def rule = fields()
                .that().areDeclaredInClassesThat().areNotEnums()    //ENUM values are treated as fields
                .should().bePrivate()
                .allowEmptyShould(true)
        expect:
            rule.check(allClasses)
    }

    def 'controllers should be in interfaces.rest package'() {
        given:
            def rule = classes()
                .that().areAnnotatedWith(RestController.class)
                .or().areAnnotatedWith(Controller.class)
                .should().resideInAPackage("${INTERFACES_PACKAGE}.rest..")
                .allowEmptyShould(true)
        expect:
            rule.check(allClasses)
    }

    def 'configuration classes should end with Configuration'() {
        given:
            def rule = classes()
                .that().areAnnotatedWith(Configuration.class)
                .should().haveSimpleNameEndingWith('Configuration')
                .allowEmptyShould(true)
        expect:
            rule.check(allClasses)
    }

    def 'Controller and RestController classes should end with Controller'() {
        given:
            def rule = classes()
                .that().areAnnotatedWith(RestController.class)
                .or().areAnnotatedWith(Controller.class)
                .should().haveSimpleNameEndingWith('Controller')
                .allowEmptyShould(true)
        expect:
            rule.check(allClasses)
    }

    @Unroll
    def 'Autowired annotations is not used on #DESCRIPTION'() {
        given:
            def rule = CODE_UNIT.should()
                .notBeAnnotatedWith(Autowired.class)
                .allowEmptyShould(true)
        expect:
            rule.check(allClasses)
        where:
            CODE_UNIT      || DESCRIPTION
            constructors() || 'constructors'
            fields()       || 'fields'
            methods()      || 'methods'
    }

    private static String[] getAllowedPackages(String domainPackage) {
        def copy = []
        copy.addAll(PACKAGES_ALLOWED_IN_DOMAIN_MATCHER)
        copy << domainPackage
        return copy
    }

}
