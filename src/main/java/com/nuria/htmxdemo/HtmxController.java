package com.nuria.htmxdemo;

import com.nuria.htmxdemo.model.CaseNotification;
import com.nuria.htmxdemo.model.CountryMessage;
import com.nuria.htmxdemo.service.ProcessorCases;
import io.github.wimdeblauwe.hsbt.mvc.HtmxRequest;
import io.github.wimdeblauwe.hsbt.mvc.HxRequest;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class HtmxController {

    @PostMapping("/search")
    @HxRequest
    public String htmxRequest(HtmxRequest details, @RequestBody String searchTextUser, Model model) {
        String searchText = searchTextUser.substring(searchTextUser.indexOf("=")+1);
        List<String> countries = List.of("Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
                "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria",
                "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus",
                "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina",
                "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi",
                "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic",
                "Chad", "Chile", "China", "Colombia", "Comoros", "Congo",
                "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus",
                "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic",
                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea",
                "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France",
                "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada",
                "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras",
                "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq",
                "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
                "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan",
                "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya",
                "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar", "Malawi",
                "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands",
                "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova",
                "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique",
                "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand",
                "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia",
                "Norway");
        countries = countries.stream()
                .filter(country -> country.toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toList());
        model.addAttribute("countries", countries);
        return "search-results";
    }

    @MessageMapping("/sendCountryNotification")
    @SendTo("/topic/notifications")
    public CaseNotification greeting(CountryMessage countryMessage) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new ProcessorCases(countryMessage.country()).process();
    }

    @GetMapping("/htmx-socket")
    public String showSocket() {
        return "htmx-socket";
    }

    @GetMapping("/search-countries")
    public String showSearchCountries() {
        return "search-countries";
    }
}
