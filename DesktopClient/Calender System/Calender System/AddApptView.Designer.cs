namespace Calender_System
{
    partial class AddApptView
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dtpDate = new System.Windows.Forms.DateTimePicker();
            this.btnBack = new System.Windows.Forms.Button();
            this.btnAdd = new System.Windows.Forms.Button();
            this.lblTitle = new System.Windows.Forms.Label();
            this.lblPat = new System.Windows.Forms.Label();
            this.lblDateTime = new System.Windows.Forms.Label();
            this.lblDur = new System.Windows.Forms.Label();
            this.txtPat = new System.Windows.Forms.TextBox();
            this.txtDur = new System.Windows.Forms.TextBox();
            this.SuspendLayout();
            // 
            // dtpDate
            // 
            this.dtpDate.Location = new System.Drawing.Point(153, 90);
            this.dtpDate.MinDate = new System.DateTime(2011, 4, 25, 0, 0, 0, 0);
            this.dtpDate.Name = "dtpDate";
            this.dtpDate.Size = new System.Drawing.Size(200, 20);
            this.dtpDate.TabIndex = 1;
            this.dtpDate.Value = new System.DateTime(2011, 4, 25, 0, 42, 22, 0);
            this.dtpDate.ValueChanged += new System.EventHandler(this.dtpDate_ValueChanged);
            // 
            // btnBack
            // 
            this.btnBack.Location = new System.Drawing.Point(50, 220);
            this.btnBack.Name = "btnBack";
            this.btnBack.Size = new System.Drawing.Size(100, 38);
            this.btnBack.TabIndex = 3;
            this.btnBack.Text = "Return To Menu";
            this.btnBack.UseVisualStyleBackColor = true;
            // 
            // btnAdd
            // 
            this.btnAdd.Location = new System.Drawing.Point(250, 220);
            this.btnAdd.Name = "btnAdd";
            this.btnAdd.Size = new System.Drawing.Size(100, 38);
            this.btnAdd.TabIndex = 4;
            this.btnAdd.Text = "Add Appointment";
            this.btnAdd.UseVisualStyleBackColor = true;
            // 
            // lblTitle
            // 
            this.lblTitle.Font = new System.Drawing.Font("Microsoft Sans Serif", 14.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point, ((byte)(0)));
            this.lblTitle.Location = new System.Drawing.Point(50, 0);
            this.lblTitle.Name = "lblTitle";
            this.lblTitle.Size = new System.Drawing.Size(300, 24);
            this.lblTitle.TabIndex = 49;
            this.lblTitle.Text = "Add Appointment";
            this.lblTitle.TextAlign = System.Drawing.ContentAlignment.MiddleCenter;
            // 
            // lblPat
            // 
            this.lblPat.AutoSize = true;
            this.lblPat.Location = new System.Drawing.Point(60, 50);
            this.lblPat.Name = "lblPat";
            this.lblPat.Size = new System.Drawing.Size(64, 13);
            this.lblPat.TabIndex = 50;
            this.lblPat.Text = "Patient\'s ID:";
            // 
            // lblDateTime
            // 
            this.lblDateTime.AutoSize = true;
            this.lblDateTime.Location = new System.Drawing.Point(15, 90);
            this.lblDateTime.Name = "lblDateTime";
            this.lblDateTime.Size = new System.Drawing.Size(109, 13);
            this.lblDateTime.TabIndex = 51;
            this.lblDateTime.Text = "Time Of Appointment:";
            // 
            // lblDur
            // 
            this.lblDur.AutoSize = true;
            this.lblDur.Location = new System.Drawing.Point(74, 130);
            this.lblDur.Name = "lblDur";
            this.lblDur.Size = new System.Drawing.Size(50, 13);
            this.lblDur.TabIndex = 52;
            this.lblDur.Text = "Duration:";
            // 
            // txtPat
            // 
            this.txtPat.Location = new System.Drawing.Point(150, 50);
            this.txtPat.Name = "txtPat";
            this.txtPat.Size = new System.Drawing.Size(200, 20);
            this.txtPat.TabIndex = 0;
            // 
            // txtDur
            // 
            this.txtDur.Location = new System.Drawing.Point(153, 130);
            this.txtDur.Name = "txtDur";
            this.txtDur.Size = new System.Drawing.Size(200, 20);
            this.txtDur.TabIndex = 2;
            // 
            // AddApptView
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(384, 262);
            this.Controls.Add(this.txtDur);
            this.Controls.Add(this.txtPat);
            this.Controls.Add(this.lblDur);
            this.Controls.Add(this.lblDateTime);
            this.Controls.Add(this.lblPat);
            this.Controls.Add(this.lblTitle);
            this.Controls.Add(this.btnAdd);
            this.Controls.Add(this.btnBack);
            this.Controls.Add(this.dtpDate);
            this.Name = "AddApptView";
            this.Text = "Add Appointment";
            this.Load += new System.EventHandler(this.AddApptView_Load);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DateTimePicker dtpDate;
        private System.Windows.Forms.Button btnBack;
        private System.Windows.Forms.Button btnAdd;
        private System.Windows.Forms.Label lblTitle;
        private System.Windows.Forms.Label lblPat;
        private System.Windows.Forms.Label lblDateTime;
        private System.Windows.Forms.Label lblDur;
        private System.Windows.Forms.TextBox txtPat;
        private System.Windows.Forms.TextBox txtDur;
    }
}