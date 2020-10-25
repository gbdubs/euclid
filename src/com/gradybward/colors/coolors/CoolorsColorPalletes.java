package com.gradybward.colors.coolors;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.gradybward.colors.Color;

/* 
 You can generate coolors.txt by running this javascript in the console on 
 
 https://coolors.co/palettes/popular
 https://coolors.co/palettes/trending

function getColorsFromWrapper(wrapper) {
  const colorElements = $(".explore-palette_colors div", wrapper);
  let styles = "";
  for (let i = 0; i < colorElements.length; i++) {
    const style = colorElements[i].getAttribute("style");
    styles = styles + style.replace(";", "").replace("background: ", "").replace("rgb(", "").replace(")","").replace(" ", "");
    if (i != colorElements.length - 1) {
      styles = styles + ";";
    }
  }
  return styles;
}
 
function getRatingFromWrapper(wrapper) {
  const savesElement = $(".explore-palette_info_saves", wrapper);
  return savesElement.text().replace(",","").replace("saves", "").replace("save", "").trim(); 
}
 
function getPalleteFromWrapper(wrapper) {
  return getRatingFromWrapper(wrapper) + ";" + getColorsFromWrapper(wrapper);
} 

function getAllPallettes() {
  const result = [];
  const wrappers = $(".explore-palette");
  for (let i = 0; i < wrappers.length; i++) {
    result.push(getPalleteFromWrapper(wrappers[i]));
  }
  return result;
}

function createCoolorsPalleteFileContents() {
  const allPalletes = [...new Set(getAllPallettes())];
  let fileContents = "";
  for (let i = 0; i < allPalletes.length; i++) {
      fileContents += allPalletes[i];
      if (i != allPalletes.length - 1) {
        fileContents += "\n"
      }
  }
  return fileContents;
}

function downloadText(fileName, fileContents) {
  var link = document.createElement('a');
  link.setAttribute('href', 'data:text/plain,' + fileContents);
  link.setAttribute('download', fileName);
  document.getElementsByTagName("body")[0].appendChild(link).click();
}
 
downloadText("coolors.txt", createCoolorsPalleteFileContents());
 */

public final class CoolorsColorPalletes {
  
  private static final String COOLORS_DATA_FILE = "coolors.txt";
  private static final List<CoolorsColorPallete> ALL_PALLETS;
  
  static {
    ALL_PALLETS = new ArrayList<>();
    File f = new File(COOLORS_DATA_FILE);
    Scanner scan;
    try {
      scan = new Scanner(f);
    } catch (FileNotFoundException e) {
      throw new RuntimeException(e);
    }
    Set<String> unique = new HashSet<>();
    while (scan.hasNextLine()) {
      unique.add(scan.nextLine());
    }
    for (String u : unique) {
      ALL_PALLETS.add(decodeFromLine(u));
    }
    System.out.printf("Loaded %d colors from COOLORs text file.\n", ALL_PALLETS.size());
    scan.close();
  }
      
  private static CoolorsColorPallete decodeFromLine(String line) {
    String[] splits = line.split(";");
    int saves = Integer.parseInt(splits[0]);
    List<Color> colors = new ArrayList<>();
    for (int i = 1; i < splits.length; i++) {
      String[] chans = splits[i].split(",");
      colors.add(Color.of(new java.awt.Color(parse(chans[0]),parse(chans[1]),parse(chans[2]))));
    }
    return new CoolorsColorPallete(colors, saves);
  }
  
  private static int parse(String s) {
    return Integer.parseInt(s.trim());
  }
  
  public static CoolorsColorPallete getRandomPallete() {
    return ALL_PALLETS.get((int) Math.floor(Math.random() * ALL_PALLETS.size()));
  }
}