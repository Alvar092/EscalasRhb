import json
import xml.etree.ElementTree as ET
import xml.dom.minidom
from pathlib import Path

# Ruta del archivo xcstrings
INPUT_FILE = "localization/Berg.xcstrings"
OUTPUT_FILE_ES = "app/src/main/res/values/strings_berg.xml"
OUTPUT_FILE_EN = "app/src/main/res/values-en/strings_berg.xml"

def normalize_key(key: str) -> str:
    return key.lower().replace(".", "_").replace("-", "_")

def main():
    with open(INPUT_FILE, "r", encoding="utf-8") as f:
        data = json.load(f)

    resources = ET.Element("resources")
    resources_en = ET.Element("resources")

    strings = data.get("strings", {})

    for key, value in strings.items():
        localizations = value.get("localizations", {})

        # Español
        es = localizations.get("es", {})
        string_unit_es = es.get("stringUnit", {})
        text_es = string_unit_es.get("value", "")

        # Inglés
        en= localizations.get("en", {})
        string_unit_en = en.get("stringUnit", {})
        text_en = string_unit_en.get("value","")

        android_key = normalize_key(key)

        string_element = ET.SubElement(resources, "string", name=android_key)
        string_element.text = text_es

        string_element_en = ET.SubElement(resources_en, "string", name=android_key)
        string_element_en.text = text_en


    # Convertimos a string y lo "prettificamos"
    xml_string = ET.tostring(resources, encoding="utf-8")
    parsed_es = xml.dom.minidom.parseString(xml_string)
    pretty_xml_es = parsed_es.toprettyxml(indent="    ")

    xml_string_en = ET.tostring(resources_en, encoding="utf-8")
    parsed_en = xml.dom.minidom.parseString(xml_string_en)
    pretty_xml_en = parsed_en.toprettyxml(indent="    ")

    # Guardamos los archivos finales
    with open(OUTPUT_FILE_ES, "w", encoding="utf-8") as f:
        f.write(pretty_xml_es)

    with open(OUTPUT_FILE_EN, "w", encoding="utf-8") as f:
        f.write(pretty_xml_en)

    print(f"Archivo generado: {OUTPUT_FILE_ES}")
    print(f"Archivo generado: {OUTPUT_FILE_EN}")

if __name__ == "__main__":
    main()