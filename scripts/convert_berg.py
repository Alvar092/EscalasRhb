import json
import xml.etree.ElementTree as ET
import xml.dom.minidom
from pathlib import Path

# Ruta del archivo xcstrings de entrada y archivos XML de salida para español e inglés
INPUT_FILE = "localization/Berg.xcstrings"
OUTPUT_FILE_ES = "app/src/main/res/values/strings_berg.xml"
OUTPUT_FILE_EN = "app/src/main/res/values-en/strings_berg.xml"

def normalize_key(key: str) -> str:

    # Normaliza la clave para que sea compatible con Android.
    # Convierte la clave a minúsculas y reemplaza puntos y guiones por guiones bajos.

    return key.lower().replace(".", "_").replace("-", "_")

def main():
    # Abrimos y leemos el archivo JSON que contiene las cadenas localizadas
    with open(INPUT_FILE, "r", encoding="utf-8") as f:
        data = json.load(f)

    # Creamos los elementos raíz <resources> para los archivos XML de español e inglés
    resources = ET.Element("resources")
    resources_en = ET.Element("resources")

    # Obtenemos el diccionario de cadenas desde el JSON
    strings = data.get("strings", {})

    # Iteramos sobre cada clave y sus valores localizados
    for key, value in strings.items():
        localizations = value.get("localizations", {})

        # Obtenemos la cadena en español
        es = localizations.get("es", {})
        string_unit_es = es.get("stringUnit", {})
        text_es = string_unit_es.get("value", "")

        # Obtenemos la cadena en inglés
        en= localizations.get("en", {})
        string_unit_en = en.get("stringUnit", {})
        text_en = string_unit_en.get("value","")

        # Normalizamos la clave para Android
        android_key = normalize_key(key)

        # Creamos el elemento <string> para español y asignamos su texto
        string_element = ET.SubElement(resources, "string", name=android_key)
        string_element.text = text_es

        # Creamos el elemento <string> para inglés y asignamos su texto
        string_element_en = ET.SubElement(resources_en, "string", name=android_key)
        string_element_en.text = text_en


    # Convertimos los árboles XML a cadenas de bytes con codificación UTF-8
    xml_string = ET.tostring(resources, encoding="utf-8")
    xml_string_en = ET.tostring(resources_en, encoding="utf-8")

    # Usamos minidom para parsear las cadenas XML y aplicar indentación para mejor legibilidad
    parsed_es = xml.dom.minidom.parseString(xml_string)
    pretty_xml_es = parsed_es.toprettyxml(indent="    ")

    parsed_en = xml.dom.minidom.parseString(xml_string_en)
    pretty_xml_en = parsed_en.toprettyxml(indent="    ")

    # Escribimos los archivos XML finales con las cadenas indentadas y codificación UTF-8
    with open(OUTPUT_FILE_ES, "w", encoding="utf-8") as f:
        f.write(pretty_xml_es)

    with open(OUTPUT_FILE_EN, "w", encoding="utf-8") as f:
        f.write(pretty_xml_en)

    # Mensajes de confirmación de generación de archivos
    print(f"Archivo generado: {OUTPUT_FILE_ES}")
    print(f"Archivo generado: {OUTPUT_FILE_EN}")

if __name__ == "__main__":
    main()