/**
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
	public static String version()      {  return "v0.4.4"  }

include 'asynchttp_v1'
definition(
	name: "Get STAttributes App",
	namespace: "csteele-PD",
	author: "c steele",
	description: "Gets and displays device attributes.",
	category: "My Apps",
	iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
	iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
	iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png"
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
		section("--= ${app.label} =--"){}
		if (state?.serverInstalled == null || state.serverInstalled == false)
		{
			section("${app.label} Installed!")
			{
				paragraph "Click Done to save then return to ${app.label} to continue."
			}
			return
		}
		section() {}
		section 
		{    
			paragraph title: "Device Attr display",
			"Pick a device to see what it supports"
//			input "pickedThis", "capability.*", title: "Select a device", multiple: true, required: true
			input "pickedThis", "capability.alarm", title: "Select a alarm device", multiple: true, required: true
			input "pickedThis", "capability.battery", title: "Select a battery device", multiple: true, required: true
			input "pickedThis", "capability.button", title: "Select a button device", multiple: true, required: true
			input "pickedThis", "capability.color", title: "Select a color device", multiple: true, required: true
			input "pickedThis", "capability.level", title: "Select a level device", multiple: true, required: true
			input "pickedThis", "capability.lock", title: "Select a lock device", multiple: true, required: true
			input "pickedThis", "capability.motion", title: "Select a motion device", multiple: true, required: true
			input "pickedThis", "capability.power", title: "Select a power device", multiple: true, required: true
			input "pickedThis", "capability.presence", title: "Select a presence device", multiple: true, required: true
			input "pickedThis", "capability.pressure", title: "Select a pressure device", multiple: true, required: true
			input "pickedThis", "capability.pushed", title: "Select a pushed device", multiple: true, required: true
			input "pickedThis", "capability.sensor", title: "Select a sensor device", multiple: true, required: true
			input "pickedThis", "capability.shock", title: "Select a shock device", multiple: true, required: true
			input "pickedThis", "capability.smoke", title: "Select a smoke device", multiple: true, required: true
			input "pickedThis", "capability.sound", title: "Select a sound device", multiple: true, required: true
			input "pickedThis", "capability.switch", title: "Select a switch device", multiple: true, required: true
			input "pickedThis", "capability.tamper", title: "Select a tamper device", multiple: true, required: true
			input "pickedThis", "capability.valve", title: "Select a valve device", multiple: true, required: true

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
	def htmlstrstr = ""
	if (pickedThis) {
		pickedThis.unique().sort().each
		{
			v ->
			  htmlstr += "Device: $v\n-= Capabilities =-\n"
			  htmlstr += "${v?.capabilities}\n"
		  
			  htmlstr += "-= Attributes =-\n"
			  htmlstr += "${v?.supportedAttributes}\n"
		  
			  htmlstr += "-= Commands =-\n"
			  htmlstr += "${v?.supportedCommands}\n"
		
		}
	}

	dynamicPage(name: "reportPage", title: "Device Report", uninstall: false, install: false)
	{
		section()
		{
			paragraph "${htmlstr}"
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
		paragraph "Developed by: C Steele\nVersion Status: $state.Status\nCurrent Version: ${version()} -  ${thisCopyright}"
	}
}



def getThisCopyright(){"&copy; 2019 C Steele "}