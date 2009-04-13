Attribute VB_Name = "Export"
Sub Export()
Dim result As XlXmlExportResult
Dim exportDir
Dim formName

formName = Worksheets("Curation Worksheet").Cells(2, 1)

If Not Validation.CheckData Then
    MsgBox ("The Data Validation process found some issues.  Please check your sheet for comments on affected cells.")

ElseIf (formName = "") Then
    MsgBox ("Please enter the form name")
    Worksheets("Curation Worksheet").Cells(2, 1).Select
Else

    Application.FileDialog(msoFileDialogFolderPicker).Title = "Please select the directory to export to"
    
    If (Application.FileDialog(msoFileDialogFolderPicker).SelectedItems.Count > 0) Then
        Application.FileDialog(msoFileDialogFolderPicker).InitialFileName = Application.FileDialog(msoFileDialogFolderPicker).SelectedItems(1)
    End If
    
    Application.FileDialog(msoFileDialogFolderPicker).Show
    
    If (Application.FileDialog(msoFileDialogFolderPicker).SelectedItems.Count > 0) Then
        exportDir = Application.FileDialog(msoFileDialogFolderPicker).SelectedItems(1)
    End If
    
    If exportDir <> "" Then
        result = ActiveWorkbook.XmlMaps("Form_Map").Export(exportDir + "\" + formName + ".xml", "True")
        
        If result = XlXmlExportResult.xlXmlExportSuccess Then
            MsgBox ("Successfully exported to " + exportDir)
        Else
            MsgBox ("Export failed!")
        End If
    End If

End If

End Sub

Sub ExportWithoutValidation()
Dim result As XlXmlExportResult
Dim exportDir
Dim formName

formName = Worksheets("Curation Worksheet").Cells(2, 1)

'If Not Validation.CheckData Then
'    MsgBox ("The Data Validation process found some issues.  Please check your sheet for comments on affected cells.")

If (formName = "") Then
    MsgBox ("Please enter the form name")
    Worksheets("Curation Worksheet").Cells(2, 1).Select
Else

    Application.FileDialog(msoFileDialogFolderPicker).Title = "Please select the directory to export to"
    
    If (Application.FileDialog(msoFileDialogFolderPicker).SelectedItems.Count > 0) Then
        Application.FileDialog(msoFileDialogFolderPicker).InitialFileName = Application.FileDialog(msoFileDialogFolderPicker).SelectedItems(1)
    End If
    
    Application.FileDialog(msoFileDialogFolderPicker).Show
    
    If (Application.FileDialog(msoFileDialogFolderPicker).SelectedItems.Count > 0) Then
        exportDir = Application.FileDialog(msoFileDialogFolderPicker).SelectedItems(1)
    End If
    
    If exportDir <> "" Then
        result = ActiveWorkbook.XmlMaps("Form_Map").Export(exportDir + "\" + formName + ".xml", "True")
        
        If result = XlXmlExportResult.xlXmlExportSuccess Then
            MsgBox ("Successfully exported to " + exportDir)
        Else
            MsgBox ("Export failed!")
        End If
    End If

End If

End Sub

