using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

using System.Net;
using System.IO;
using System.Xml;

namespace Calender_System
{
    public partial class LoginView : Form
    {
        private String baseURI = "http://simon.ist.rit.edu:8080/BeerService/resources/Services/Beers/";
        
        public LoginView()
        {
            InitializeComponent();
        }

        private void btnLogin_Click(object sender, EventArgs e)
        {
            ServiceMethod("GET", baseURI + "Costliest");
        }

        //Find the cheapest beer
        void ServiceMethod(String methodType, String uri)
        {
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(uri);
                request.Method = methodType;
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                txtName.Text = ConvertResponse(response);
                txtPass.Text = "";
            }
            catch (Exception)
            {
                txtName.Text = "ERROR: Could not connect to service.";
                txtPass.Text = "";
            }
        }

        //Converts xml of response stream into string 
        String ConvertResponse(HttpWebResponse response)
        {
            String outputString = "";
            String result = new StreamReader(response.GetResponseStream()).ReadToEnd();
            using (XmlReader reader = XmlReader.Create(new StringReader(result)))
            {
                XmlWriterSettings ws = new XmlWriterSettings();
                // Parse the file and display each of the nodes.
                while (reader.Read())
                {
                    switch (reader.NodeType)
                    {
                        case XmlNodeType.Text:
                            outputString += reader.Value + "\n";
                            break;
                    }
                }
            }
            return outputString;
        }
    }
}
