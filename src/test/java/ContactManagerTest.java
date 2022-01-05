
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//Позволяет создавать инстанс contactManager один на весь тест-класс для @BeforeEach,
//и удаляем статик в @BeforeAll и @AfterAll методах
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContactManagerTest {
    ContactManager contactManager; //иниализация переменной contactManager
    @BeforeAll
    public void setUp() {
        System.out.println("Execution at the beginning of the test-class");
    }
    @BeforeEach
    public void setupInstance() {
        //перед каждым методом создаём новый инстанс оф ContactManager, но см.выше
        contactManager = new ContactManager();
        System.out.println("Execution before each test. Check instance: "+ this);
    }


    //    @Test - можно удалить, т.к. есть     @ParameterizedTest ниже
    @DisplayName("Check creation of a new contact with annotation @ValueSource")
    //условие выполнение теста на указанной системе
    @DisabledOnOs(value = OS.MAC, disabledReason = "Only for Windows")
    @ParameterizedTest //параметризированный тест с разными номерами телефонов на входе
    @ValueSource(strings = {"0501111111", "0502222222", "0673333333", "0934444444"})
    void createContactByValueSource(String phoneNumber) {
        //Установка системных свойств и следующее 'предположение' верного условия, и тест далее выполняется
        System.setProperty("ENV", "DEV");
        Assumptions.assumeTrue("DEV".equals(System.getProperty("ENV")));

        System.out.println("TestCreateContactByValueSource: Check instance: "+ this);
        //ContactManager contactManager = new ContactManager(); - переносим в @BeforeEach
        contactManager.addContact("Alex", "Black", phoneNumber);

        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
        //или проверить по другой библиотеке AssertJ
        assertThat(contactManager.getAllContacts().size()).isEqualTo(1);
    }

    @DisplayName("Check creation of a new contact with annotation @MethodSource")
    @ParameterizedTest //параметризированный тест с разными номерами телефонов из другого метода, как источника
    @MethodSource("phoneNumberList")
    public void createContactByMethodSource(String phoneNumber) {
        System.out.println("TestСreateContacBytMethodSource: Check instance: "+ this);
        contactManager.addContact("Alex", "Black", phoneNumber);

        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }
    //Метод, как источник данных для теста createContactByMethodSource
    private List<String> phoneNumberList() {
        return Arrays.asList("0501111111", "0502222222", "0673333333", "0934444444");
    }


    @DisplayName("Check creation of a new contact with annotation @CsvSource")
    @ParameterizedTest //параметризированный тест с разными номерами телефонов на входе
    @CsvSource ({"0501111111", "0502222222", "0673333333", "0934444444", "0955555555"})
    void createContactByCsvSource(String phoneNumber) {
        System.out.println("TestCreateContactByCsvSource: Check instance: "+ this);
        contactManager.addContact("Alex", "Black", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @DisplayName("Check creation of a new contact with annotation @CsvFileSource")
    @ParameterizedTest //параметризированный тест с разными номерами телефонов на входе из отд. файла
    @CsvFileSource(resources = "/ContactPhoneNumberData.csv")
    void createContactByCsvFileSource(String phoneNumber) {
        System.out.println("TestCreateContactByCsvFileSource: Check instance: "+ this);
        contactManager.addContact("Alex", "Black", phoneNumber);
        assertFalse(contactManager.getAllContacts().isEmpty());
        assertEquals(1, contactManager.getAllContacts().size());
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Execution after each test: Check instance: "+ this);
    }
    @AfterAll
    public void tearDownAll() {
        System.out.println("Execution at the end of the test-class: Check instance: "+ this);
    }


@Nested //вложенный класс, сгруппирированный как отдельно логический
class NegativeNestedClass {

    @Test //проверяем т.н. негативное событие: ожидаем выброс RuntimeException при условии ..., и тест - Passed
    @DisplayName("Negative case: Should not create contact when first name is null")
    public void shouldThrowRuntimeExceptionWhenFirstNameIsNull() {
        System.out.println("TestShouldThrowRuntimeExceptionWhenFirstNameIsNull: Check instance: "+ this);
        assertThrows(RuntimeException.class, () -> {contactManager.addContact(
                null,
                "Doe",
                "0509999999");});
    }
    //проверяем т.н. негативное событие: выброс RuntimeException при условии ..., и тест - Passed
    @DisplayName("Negative case: Should not create contact when last name is null")
    @RepeatedTest(value = 3)//повторение данного теста три раза
    public void shouldThrowRuntimeExceptionWhenLastNameIsNull() {
        System.out.println("TestShouldThrowRuntimeExceptionWhenLastNameIsNull: Check instance: "+ this);
        assertThrows(RuntimeException.class, () -> {contactManager.addContact(
                "John",
                null,
                "0509999999");});
    }

    @Test //проверяем т.н. негативное событие: выброс RuntimeException при условии ..., и тест - Passed
    @DisplayName("Negative case: Should not create contact when PhoneNumber is null")
    public void shouldThrowRuntimeExceptionWhenPhoneNumberIsNull() {
        System.out.println("TestShouldThrowRuntimeExceptionWhenPhoneNumberIsNull: Check instance: "+ this);
        assertThrows(RuntimeException.class, () -> {contactManager.addContact(
                "John",
                "Doe",
                null);});
    }
}
}