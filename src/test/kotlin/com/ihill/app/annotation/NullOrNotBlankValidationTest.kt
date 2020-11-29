//package com.ihill.app.annotation
//
//import com.newagesol.profile.web.validation.annotations.NullOrNotBlank
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.jupiter.api.BeforeEach
//import org.junit.jupiter.api.Test
//import org.springframework.test.context.ActiveProfiles
//import javax.validation.Validation
//import javax.validation.Validator
//
//@ActiveProfiles("test")
//class NullOrNotBlankValidationTest {
//
//    private lateinit var validator: Validator
//
//    @BeforeEach
//    fun before() {
//        validator = Validation.buildDefaultValidatorFactory().getValidator();
//    }
//
//    @Test
//    fun `should be valid with nullable field`() {
//        // given
//        val dto = CanBeNullOrNotEmptyDto(someField = null)
//
//        // when
//        val errorSet = validator.validate(dto)
//
//        // then
//        assertThat(errorSet.size).isEqualTo(0)
//    }
//
//    @Test
//    fun `should be valid with presented field`() {
//        // given
//        val dto = CanBeNullOrNotEmptyDto(someField = "abra-cadabra")
//
//        // when
//        val errorSet = validator.validate(dto)
//
//        // then
//        assertThat(errorSet.size).isEqualTo(0)
//    }
//
//    @Test
//    fun `should be invalid with empty field`() {
//        // given
//        val dto = CanBeNullOrNotEmptyDto(someField = "")
//
//        // when
//        val errorSet = validator.validate(dto)
//
//        // then
//        assertThat(errorSet.size).isEqualTo(1)
//    }
//
//    @Test
//    fun `should be invalid with blank field`() {
//        // given
//        val dto = CanBeNullOrNotEmptyDto(someField = "  ")
//
//        // when
//        val errorSet = validator.validate(dto)
//
//        // then
//        assertThat(errorSet.size).isEqualTo(1)
//    }
//}
//
//data class CanBeNullOrNotEmptyDto(
//    @field:NullOrNotBlank
//    val someField: String? = null
//)