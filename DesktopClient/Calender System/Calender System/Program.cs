using System;
using System.Collections.Generic;
using System.Linq;
using System.Windows.Forms;

using System.Net;
using System.IO;
using System.Xml;

namespace Calender_System
{
    static class Program
    {
        public static String baseURI_AS = "http://simon.ist.rit.edu:8080/AppointmentSystem/resources/appointmentservice/";
        
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new LoginView());
        }

        //Runs a service method at the supplied URI
        //Service must respond in text/plain
        public static String ServiceMethod(String methodType, String uri)
        {
            String responseString = "";
            try
            {
                HttpWebRequest request = (HttpWebRequest)WebRequest.Create(uri);
                request.Method = methodType;
                HttpWebResponse response = (HttpWebResponse)request.GetResponse();
                responseString = new StreamReader(response.GetResponseStream()).ReadToEnd(); ;
            }
            //Only will fail if service is down
            catch (Exception)
            {
                responseString = "ERROR";
            }
            return responseString;
        }
    }
}
