/**
 * IMPORT URL: https://raw.githubusercontent.com/HubitatCommunity/GetAttributesApp/master/GetAttributesApp.groovy
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 *
*/

/*
 * csteele	v0.4.4	Remove updateCheck() - let HPM check for an update.
 *
 */
 
	public static String version()      {  return "v0.4.4"  }


definition(
	name: "Get Attributes App",
	namespace: "csteele-PD",
	author: "c steele",
	description: "Gets and displays device attributes.",
	category: "My Apps",
	iconUrl: "",
	iconX2Url: "",
	iconX3Url: ""
)


// Preference pages
preferences
{
	page(name:"mainPage")
	page(name: "reportPage")
}


/*
	mainPage
    
	Purpose: Displays the main (landing) page.

	Notes: 	Not very exciting.
*/
def mainPage() {
	dynamicPage(name: "mainPage", uninstall: true, install: true)
	{
		section("<h2>${app.label}</h2>"){}
		if (state?.serverInstalled == null || state.serverInstalled == false)
		{
			section("<b style=\"color:green\">${app.label} Installed!</b>")
			{
				paragraph "Click <i>Done</i> to save then return to ${app.label} to continue."
			}
			return
		}
		section(getFormat("title", " ${app.label}")) {}
		section 
		{    
			paragraph title: "Device Attr display",
			"<b>Pick a device to see what it supports</b>"
			input "pickedThis", "capability.*", title: "Select a device", multiple: true, required: true
       	}
		section
		{
			href "reportPage", title: "Device Capability Report", description: "Lists all capabilities of the device's driver..."
      	}
      	display()
	} 
}



/*
	reportPage
    
	Purpose: Displays the Report page.

	Notes: 	Not very exciting.
*/
def reportPage()
{
	dynamicPage(name: "reportPage", title: "Device Report", uninstall: false, install: false)
	{
		section()
		{
			html = "<table style=\"width:100%\"><tbody>"
			pickedThis.unique().sort().each
			{
				v ->
				  html += "<thead><tr><th style=\"width:25%\">Device</th><td>-= <b>Capabilities</b> =-</td></tr></thead>"
				  html += "<tr><td><div style='color:#1A77C9;text-align:center'>$v</div></td><td>${v?.capabilities}</td></tr>"
			  
				  html += "<tr><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>-= <b>Attributes</b> =-</td></tr>"
				  html += "<tr><td>&nbsp;</td><td>${v?.supportedAttributes}</td></tr>"
			  
				  html += "<tr><td>&nbsp;</td><td>&nbsp;</td></tr><tr><td>&nbsp;</td><td>-= <b>Commands</b> =-</td></tr>"
				  html += "<tr><td>&nbsp;</td><td>${v?.supportedCommands}</td></tr>"
		
				  html += "<tr><td colspan=2><hr style='background-color:#1A77C9; height: 1px; border: 0;'></hr></td></tr>"
				  html += "<tr><td>&nbsp;</td><td>&nbsp;</td></tr>"
		
			}
			paragraph "${html}</tbody></table>"
		}
	}
}


def getFormat(type, myText=""){
    if(type == "header-green") return "<div style='color:#ffffff;font-weight: bold;background-color:#81BC00;border: 1px solid;box-shadow: 2px 3px #A9A9A9'>${myText}</div>"
    if(type == "line") return "\n<hr style='background-color:#1A77C9; height: 1px; border: 0;'></hr>"
    if(type == "title") return "<h2 style='color:#1A77C9;font-weight: bold'>${myText}</h2>"
}


/*
	installed
    
	Purpose: Standard install function.

	Notes: Doesn't do much.
*/
def installed()
{
	log.info "Installed"
	state.serverInstalled = true
	initialize()
}


/*
	updated
    
	Purpose: Standard update function.

	Notes: Still doesn't do much.
*/
def updated()
{
	log.info "Updated with settings: ${settings}"
	unsubscribe()
	initialize()
}


/*
	initialize
    
	Purpose: Initialize the server instance.

	Notes: Does it all
*/
def initialize()
{
	// nothing needed
}


/*
	display
    
	Purpose: Displays the title/copyright/version info

	Notes: 	Not very exciting.
*/
def display() {
	section{
		paragraph "\n<hr style='background-color:#1A77C9; height: 1px; border: 0;'></hr>"
		paragraph "<div style='color:#1A77C9;text-align:center;font-weight:small;font-size:9px'>Developed by: C Steele<br/>Version Status: $state.Status<br>Current Version: ${version()} -  ${thisCopyright}</div>"
	}
}

def getThisCopyright(){"&copy; 2019 C Steele "}
