package ro.axonsoft.accsecond.helpers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
public class PersonHelperTest {

    @Test
    public void testComputePersonMap() {

        HashMap<String, String> inputMap = new HashMap<>();

        String checkIfStringContainsDigits = "String do not contains digits";
        PersonHelper.computePerson(checkIfStringContainsDigits, inputMap);
        assertTrue(true, String.valueOf(inputMap.isEmpty()));

        String checkNameSplit = "Dr. Frank von Supper (fsupper)";
        PersonHelper.computePerson(checkNameSplit, inputMap);
        assertEquals("Frank", inputMap.get("firstName"));
        assertEquals("Supper", inputMap.get("lastName"));
        assertEquals("Dr.", inputMap.get("title"));
        assertEquals("von", inputMap.get("nameAddition"));
        assertEquals("fsupper", inputMap.get("ldapUser"));

        String checkThreeNames = "Dennis Fischer (dfischer)";
        PersonHelper.computePerson(checkThreeNames, inputMap);
        assertEquals("Dennis", inputMap.get("firstName"));
        assertEquals("Fischer", inputMap.get("lastName"));
        assertEquals("", inputMap.get("title"));
        assertEquals("", inputMap.get("nameAddition"));
        assertEquals("dfischer", inputMap.get("ldapUser"));

        String concatenatingName = "Max Mustermann-Hoffmann (mmustermann)";
        PersonHelper.computePerson(concatenatingName, inputMap);
        assertEquals("Max Mustermann", inputMap.get("firstName"));
        assertEquals("Hoffmann", inputMap.get("lastName"));
        assertEquals("", inputMap.get("title"));
        assertEquals("", inputMap.get("nameAddition"));
        assertEquals("mmustermann", inputMap.get("ldapUser"));

        String nameWith = "Dr. Dennis Krannich (dkrannich)";
        PersonHelper.computePerson(nameWith, inputMap);
        assertEquals("Dennis", inputMap.get("firstName"));
        assertEquals("Krannich", inputMap.get("lastName"));
        assertEquals("Dr.", inputMap.get("title"));
        assertEquals("", inputMap.get("nameAddition"));
        assertEquals("dkrannich", inputMap.get("ldapUser"));

        String ceva = "Etienne de Ruffray (eruffray)";
        PersonHelper.computePerson(ceva, inputMap);
        assertEquals("Etienne", inputMap.get("firstName"));
        assertEquals("Ruffray", inputMap.get("lastName"));
        assertEquals("", inputMap.get("title"));
        assertEquals("de", inputMap.get("nameAddition"));
        assertEquals("eruffray", inputMap.get("ldapUser"));

        String last = "Iftikar Ahmad Khan (ikhan)";
        PersonHelper.computePerson(last, inputMap);
        assertEquals("Iftikar Ahmad", inputMap.get("firstName"));
        assertEquals("Khan", inputMap.get("lastName"));
        assertEquals("", inputMap.get("title"));
        assertEquals("", inputMap.get("nameAddition"));
        assertEquals("ikhan", inputMap.get("ldapUser"));

    }
}
