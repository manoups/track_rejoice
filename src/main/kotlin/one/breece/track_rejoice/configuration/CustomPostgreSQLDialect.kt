package one.breece.track_rejoice.configuration

import org.hibernate.boot.model.FunctionContributions
import org.hibernate.dialect.PostgreSQLDialect
import org.hibernate.query.sqm.function.FunctionKind
import org.hibernate.query.sqm.produce.function.PatternFunctionDescriptorBuilder

class CustomPostgreSQLDialect : PostgreSQLDialect() {
    override fun initializeFunctionRegistry(functionContributions: FunctionContributions) {
        super.initializeFunctionRegistry(functionContributions)
        val functionRegistry = functionContributions.functionRegistry
        val types = functionContributions.typeConfiguration
        PatternFunctionDescriptorBuilder(functionRegistry, "st_dwithin", FunctionKind.NORMAL, "ST_DWithin(?1,?2,?3)")
            .setExactArgumentCount(3)
            .setInvariantType(types.getBasicTypeForJavaType(Boolean::class.java)).register()
    }
}