package ar.edu.mercadogratis.app.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ControllerAdvice;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "ar.edu.mercadogratis.app", importOptions = { ImportOption.DoNotIncludeTests.class })
public class NamingConventionTest {
    @ArchTest
    static ArchRule serviceShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..service..")
                    .and().areAnnotatedWith(Service.class)
                    .should().haveSimpleNameEndingWith("Service");

    @ArchTest
    static ArchRule controllersShouldNotHaveGuiInName =
            classes()
                    .that().resideInAPackage("..controller..")
                    .should().haveSimpleNameNotContaining("Gui");

    @ArchTest
    static ArchRule controllersShouldBeSuffixed =
            classes()
                    .that().resideInAPackage("..controller..")
                    .or().areAnnotatedWith(Controller.class)
                    .or().areAnnotatedWith(ControllerAdvice.class)
                    .should().haveSimpleNameEndingWith("Controller");

    @ArchTest
    static ArchRule classesNamedControllerShouldBeInControllerPackage =
            classes()
                    .that().haveSimpleNameContaining("Controller")
                    .should().resideInAPackage("..controller..");
}
