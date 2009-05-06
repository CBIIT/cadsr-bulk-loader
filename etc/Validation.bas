Attribute VB_Name = "Validation"
Public Function CheckData() As Boolean

Dim firstColumn As String
Dim lastColumn As String
Dim iRowLoop As Integer
Dim intLastRow As Integer
Dim intFirstRow As Integer
Dim intRowLoop As Integer
Dim status As Boolean
Dim formTable As Range


status = True
intFirstRow = 5
firstColumn = "H"
lastColumn = "AB"

' determine last row in sheet

'Worksheets("Curation Worksheet").UsedRange.SpecialCells(xlCellTypeLastCell).Activate
'intLastRow = ActiveCell.row
Set formTable = Worksheets("Curation Worksheet").ListObjects("FormTable").Range
intLastRow = formTable.Cells(1, 1).row + formTable.Rows.Count - 1

Call ClearEWSheets
'start iterating over rows
For intRowLoop = intFirstRow To intLastRow
    If NotEmpty(intRowLoop, firstColumn, lastColumn) Then
        Call DoRules(intRowLoop, firstColumn, lastColumn)
        If DoDataRules(intRowLoop, lastColumn) Then status = False
    End If
Next intRowLoop

'If (Application.CountA(Worksheets("Error List").UsedRange) > 0 _
Or Application.CountA(Worksheets("Warning List").UsedRange) > 0) _
Then status = False

If (Application.CountA(Worksheets("Error List").UsedRange) > 0) _
Then status = False

CheckData = status
    
End Function

Private Function DoDataRules(rowNumber As Integer, endCol As String) As Boolean
    Dim firstCell As Range
    Dim lastCell As Range
    Dim mySheet As Worksheet
  
    Set mySheet = Worksheets("Curation Worksheet")
    Set firstCell = mySheet.Range("A" & CStr(rowNumber))
    Set lastCell = mySheet.Range(endCol & CStr(rowNumber))
    
    DoDataRules = CDataFormat(rowNumber, firstCell, lastCell)

End Function
Private Function CDataFormat(rowNumber As Integer, fc As Range, lc As Range) As Boolean
    Dim fCol As Integer
    Dim lCol As Integer
    Dim status As Boolean
    Dim rng As Range

    fCol = fc.Column
    lCol = lc.Column
    status = True

     For i = 0 To lCol - fCol
        iStatus = True
        Set rng = fc.Offset(0, i)
        If (Len(rng.Text) > 0) Then
            If CheckConcept(rng) Then status = False
        End If
    Next i
End Function
Private Function CheckConcept(rng As Range) As Boolean
    Dim cPattern As String
    Dim conceptual As Integer
    Dim concept As Integer
    conceptual = InStr(rng.XPath.Value, "conceptual")
    concept = InStr(rng.XPath.Value, "Concepts")
    cPattern = "^C\d{3,6}(:(\s?\w*\s?)*)?$"
    If (conceptual = 0) Then
        If (concept > 0) Then CheckConcept = CSplitConcepts(rng, cPattern)
    End If
End Function
Private Function CSplitConcepts(rng As Range, cPattern As String) As Boolean
    Dim avarSplit As Variant
    Dim intIndex As Integer
    Dim temp As String
    
    avarSplit = Split(rng.Value, ";")
    For intIndex = LBound(avarSplit) To UBound(avarSplit)
        temp = avarSplit(intIndex)
         If Not (ValidString(cPattern, Trim(temp))) Then
            Call SetErrorComment(rng, "Malformed Concept Code")
            Call SetError(rng, "Malformed Concept Code")
            CSplitConcepts = False
         End If
    Next
        
    CSplitConcepts = True
    
End Function


Public Function DoRules(rowNumber As Integer, startCol As String, endCol As String)

    Dim firstCell As Range
    Dim lastCell As Range
    Dim mySheet As Worksheet
    Dim rCDEid As Boolean
    Dim rDEC As Boolean
    Dim rVD As Boolean
    Dim rPV As Boolean
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set firstCell = mySheet.Range(startCol & CStr(rowNumber))
    Set lastCell = mySheet.Range(endCol & CStr(rowNumber))
    
    'firstCell.Value2 = "firstCell" & CStr(rowNumber)
    'lastCell.Value2 = "lastCell" & CStr(rowNumber)
    Call ClearComments(firstCell, lastCell)
    
    
    rCDEid = CRuleCDEid(firstCell, lastCell)
    If rCDEid = False Then
        rPV = CRulePV(rowNumber)
        If rPV = False Then
            rDEC = CRuleDEC(firstCell, lastCell)
            rVD = CRuleVD(firstCell, lastCell)
        End If
    End If
        
        Dim returnStatus As Boolean
    returnStatus = rCDEid Or (rDEC And rVD) Or rPV
    DoRules = returnStatus
    
End Function
Private Sub ClearComments(fc As Range, lc As Range)
    Dim fCol As Integer
    Dim lCol As Integer

    Dim rng As Range
    
    fCol = fc.Column
    lCol = lc.Column
   
    For i = 0 To lCol - fCol
        Set rng = fc.Offset(0, i)
        rng.Borders.Weight = 2
        rng.Borders.Color = 0
        'rng.ClearComments
    Next i

End Sub
Private Function CRuleDEC(fc As Range, lc As Range) As Boolean
    Dim startC As Range
    Dim endC As Range
    Dim vp As Boolean
    
    Set startC = fc.Offset(0, 3)
    Set endC = fc.Offset(0, 9)
    
    If ValidPublicId(startC) = "Blank" Then
        CRuleDEC = CFull(startC.Offset(0, 1), endC, "This cell is required to create new DEC.")
    ElseIf ValidPublicId(startC) = "Valid" Then
        CRuleDEC = CEmpty(startC.Offset(0, 1), endC, "DEC id is present, this cell will not be processed.")
    Else
        CRuleDEC = CFull(startC.Offset(0, 1), endC, "This cell is required to create new DEC.")
    End If
        
End Function
Private Function CRuleVD(fc As Range, lc As Range) As Boolean

    Dim startC As Range
    Dim endC As Range
    Dim enumC As Range
    
    Set startC = fc.Offset(0, 10)
    Set endC = fc.Offset(0, 20)
    Set enumC = fc.Offset(0, 16)
    
    If Len(startC.Text) > 0 And ValidPublicId(startC) = "Valid" Then
        If CEmpty(startC.Offset(0, 1), endC, "VD id is present, this cell will not be processed.") Then
            CRuleVD = True
        Else
            CRuleVD = False
        End If
    ElseIf Len(enumC.Text) > 0 Then
        If LCase(enumC.Text) = "yes" Then
            CRuleVD = CFull(startC.Offset(0, 1), enumC.Offset(0, -1), "This cell is required to create new Enumerated Value Domain.") And CFull(enumC.Offset(0, 1), endC, "This cell is required to create new Enumerated Value Domain.")
        ElseIf LCase(enumC.Text) = "no" Then
            CRuleVD = CFull(startC.Offset(0, 1), enumC.Offset(0, -1), "This cell is required to create new non-Enumerated Value Domain.")
        Else
            CRuleVD = False
        End If
    Else
        CRuleVD = CFull(startC.Offset(0, 1), endC, "This cell is required to create a new Value Domain.")
    End If
End Function

Private Function CRuleCDEid(fc As Range, lc As Range) As Boolean
    Dim ce As Boolean
    
    If Len(fc.Text > 0) And ValidPublicId(fc) = "Valid" Then
        Call CEmpty(fc.Offset(0, 3), lc, "CDE id is present, this cell will not be processed.")
        CRuleCDEid = True
    Else
        CRuleCDEid = False
    End If
    
End Function

Private Function CRulePV(rowNumber As Integer) As Boolean
    Dim pvCol As String
    Dim pvlCol As String
    Dim vmCol As String
    Dim mySheet As Worksheet
    Dim nEmpty As Boolean
    
    Dim pc As Range
    Dim plc As Range
    Dim vmc As Range
    
    pvCol = "W"
    pvlCol = "X"
    vmCol = "Y"
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set pc = mySheet.Range(pvCol & CStr(rowNumber))
    Set plc = mySheet.Range(pvlCol & CStr(rowNumber))
    Set vmc = mySheet.Range(vmCol & CStr(rowNumber))
    
    nEmpty = NotEmpty(rowNumber, "F", "V")
    
    If (Len(pc.Text) > 0 And Len(plc.Text) > 0 And Len(vmc.Text) > 0 And Not nEmpty) Then
        CRulePV = True
    Else
        CRulePV = False
    End If
        
End Function
Private Function CEmpty(fc As Range, lc As Range, msg As String) As Boolean
    Dim fCol As Integer
    Dim lCol As Integer
    Dim status As Boolean
    Dim rng As Range
    
    fCol = fc.Column
    lCol = lc.Column
    status = True
    
    For i = 0 To lCol - fCol
        iStatus = True
        Set rng = fc.Offset(0, i)
        If (Len(rng.Text) > 0) Then iStatus = False
        If iStatus = False Then
            status = False
            Call SetErrorComment(rng, msg)
            Call SetWarning(rng, msg)
        Else
            Call ClearError(rng)
        End If
    Next i
    
    CEmpty = status
End Function

Private Sub SetErrorComment(rng As Range, msg As String)
    Dim strGotIt As String
    
    On Error Resume Next

        'strGotIt = WorksheetFunction.Clean(rng.Comment.Text)

    On Error GoTo 0

   
    'If (Len(strGotIt) > 0) Then strGotIt = ";" + strGotIt
    
    rng.Borders.Weight = 4
    rng.Borders.Color = 4
    
    'rng.ClearComments
    'rng.AddComment (msg + strGotIt)
    'rng.Comment.Visible = True
    'rng.Comment.Shape.TextFrame.AutoSize = True
End Sub

Private Sub ClearError(rng As Range)
    rng.Borders.Weight = 2
    rng.Borders.Color = 0
    'rng.ClearComments

End Sub
Private Sub ClearEWSheets()
    Dim mySheet As Worksheet
    
    Set mySheet = Worksheets("Error List")
    mySheet.Cells.Clear
    Set mySheet = Worksheets("Warning List")
    mySheet.Cells.Clear
    
End Sub


Private Sub SetError(rng As Range, msg As String)
    Dim strGotIt As String
    Dim mySheet As Worksheet
    Dim errorRng As Range
    Dim firstRng As Range
    
    Set mySheet = Worksheets("Error List")
    'Dim Col As String
    Dim row As Integer
    'Dim lRealLastColumn As Integer
    Dim lCol As Integer
    
    'Col = Left(rng.Address(1, 0), InStr(1, rng.Address(1, 0), "$") - 1)
    
    row = rng.row
    
    Set firstRng = mySheet.Range("A" & CStr(row))
    firstRng.Value2 = "Errors for row " & CStr(row) & ":"
    firstRng.EntireColumn.AutoFit
      
    'lRealLastColumn = Cells.Find("*", firstRng, xlFormulas, , xlByColumns, xlPrevious).Column
    lCol = 1
    Do While firstRng.Offset(0, lCol) <> ""
        lCol = lCol + 1
    Loop
    
    Set errorRng = firstRng.Offset(0, lCol)
    Call MakeLink(mySheet, errorRng, "'" & rng.Worksheet.Name & "'!" & rng.Address, rng.Address & ":" & msg, msg)
    errorRng.EntireColumn.AutoFit
    
End Sub

Private Sub SetWarning(rng As Range, msg As String)
    Dim strGotIt As String
    Dim mySheet As Worksheet
    Dim errorRng As Range
    Dim firstRng As Range
    Dim lCol As Integer
    
    Set mySheet = Worksheets("Warning List")
    Dim row As Integer
    
    row = rng.row
    
    Set firstRng = mySheet.Range("A" & CStr(row))
    firstRng.Value2 = "Warnings for row " & CStr(row) & ":"
    firstRng.EntireColumn.AutoFit
      
    lCol = 1
    Do While firstRng.Offset(0, lCol) <> ""
        lCol = lCol + 1
    Loop
    Set errorRng = firstRng.Offset(0, lCol)
    
    Call MakeLink(mySheet, errorRng, "'" & rng.Worksheet.Name & "'!" & rng.Address, rng.Address & ": " & msg, msg)
    
    errorRng.EntireColumn.AutoFit
    
    
End Sub

Private Sub MakeLink(ByVal mySheet As Worksheet, ByVal cell As Range, ByVal url As String, ByVal txt As String, ByVal tooltip_text As String)
    mySheet.Hyperlinks.Add _
        Anchor:=cell, _
        Address:=ThisWorkbook.FullName, _
        SubAddress:=url, _
        ScreenTip:=tooltip_text, _
        TextToDisplay:=txt
End Sub


Private Function CFull(fc As Range, lc As Range, msg As String) As Boolean
    Dim fCol As Integer
    Dim lCol As Integer
    Dim status As Boolean
    Dim rng As Range
    
    fCol = fc.Column
    lCol = lc.Column
    status = True
    
    For i = 0 To lCol - fCol
        iStatus = True
        Set rng = fc.Offset(0, i)
        If (Len(rng.Text) = 0) Then iStatus = False
        If iStatus = False Then
            status = False
            Call SetErrorComment(rng, msg)
            Call SetWarning(rng, msg)
        Else
            Call ClearError(rng)
        End If
    Next i
    
    CFull = status
End Function


Private Function ValidString(myPattern As String, testString As String) As Boolean

Dim objRegExp As Object
Dim objMatch As Object
Dim colMatches As Object
Dim tTest As Boolean

'Create a regular expression object.
  Set objRegExp = CreateObject("VBScript.RegExp")

'Set the pattern by using the Pattern property.
  objRegExp.Pattern = myPattern

'Set Case Insensitivity.
  objRegExp.IgnoreCase = True

'Set global applicability.
  objRegExp.Global = True


tTest = objRegExp.Test(testString)

ValidString = tTest

End Function
Private Function ValidPublicId(fc As Range) As String
'Pattern for Concept ID
'Any Digit(0 to inf), space, dot, 'v', dot, single digit, dot, one digit, zero or one digit
Dim testPattern As String

testPattern = "^\d*v\.\d\.\d\d?"
If ValidString(testPattern, fc.Text) Then
    Call ClearError(fc)
    ValidPublicId = "Valid"
Else
    Call SetErrorComment(fc, "Not a Valid Id")
    
    If fc.Text = "" Then
        Call SetWarning(fc, "Id is blank.")
        ValidPublicId = "Blank"
    Else
        Call SetError(fc, fc.Text & " is not a Valid Id.")
        ValidPublicId = "Invalid"
    End If
    
End If

End Function
Public Sub ClearValidationMessages()

Dim firstColumn As String
Dim lastColumn As String
Dim iRowLoop As Integer
Dim intLastRow As Integer
Dim intFirstRow As Integer
Dim intRowLoop As Integer
Dim status As Boolean
Dim formTable As Range

intFirstRow = 5
firstColumn = "F"
lastColumn = "Z"

' determine last row in sheet

Set objExcel = CreateObject("Excel.Application")
'Worksheets("Curation Worksheet").UsedRange.SpecialCells(xlCellTypeLastCell).Activate
'intLastRow = ActiveCell.row

Set formTable = Worksheets("Curation Worksheet").ListObjects("FormTable").Range
intLastRow = formTable.Cells(1, 1).row + formTable.Rows.Count - 1

Call ClearEWSheets

'start iterating over rows
For intRowLoop = intFirstRow To intLastRow
    Call ClearRow(intRowLoop, firstColumn, lastColumn)
Next intRowLoop
    
End Sub

Public Sub ClearRow(rowNumber As Integer, startCol As String, endCol As String)

    Dim firstCell As Range
    Dim lastCell As Range
    Dim mySheet As Worksheet
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set firstCell = mySheet.Range(startCol & CStr(rowNumber))
    Set lastCell = mySheet.Range(endCol & CStr(rowNumber))

    Call ClearComments(firstCell, lastCell)
    
End Sub
Private Function ColRef(Col As String) As Integer
'
' Returns Excel column number
'
' Input:
' Col Excel column reference (eg. AA)
'
' Output:
' ColRef Column number (eg. 27)

'col needs to be upper case
    Col = UCase(Col)
   
    If Len(Col) = 1 Then
        ColRef = Asc(Col) - 64
       
    ElseIf Len(Col) = 2 Then
        C1 = Left$(Col, 1)
        ColRef1 = (Asc(C1) - 64) * 26
       
        C2 = Right$(Col, 1)
        ColRef = ColRef1 + (Asc(C2) - 64)
    End If
   
    If (ColRef > 256) Then
        MsgBox "Wrong Column number", vbExclamation
        ColRef = -1
        Exit Function
    End If

End Function
Private Function NotEmpty(rowNumber As Integer, fc As String, lc As String) As Boolean
    Dim fCol As Integer
    Dim lCol As Integer
    Dim status As Boolean
    Dim rng As Range
    Dim cell As Range
    status = False
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set cell = mySheet.Range(fc & CStr(rowNumber))
    
    fCol = ColRef(fc)
    lCol = ColRef(lc)
    
    For i = 0 To lCol - fCol
        Set rng = cell.Offset(0, i)
        txt = rng.Text
        lenTxt = Len(txt)
        If (Len(txt) > 0) Then status = True
    Next i
    NotEmpty = status
    
End Function
