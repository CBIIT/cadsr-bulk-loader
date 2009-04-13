Attribute VB_Name = "Validation"
Public Function CheckData() As Boolean

Dim firstColumn As String
Dim lastColumn As String
Dim iRowLoop As Integer
Dim intLastRow As Integer
Dim intFirstRow As Integer
Dim intRowLoop As Integer
Dim status As Boolean

intFirstRow = 5
firstColumn = "F"
lastColumn = "Z"

' determine last row in sheet

Set objExcel = CreateObject("Excel.Application")
Worksheets("Curation Worksheet").UsedRange.SpecialCells(xlCellTypeLastCell).Activate
intLastRow = ActiveCell.Row

'start iterating over rows
For intRowLoop = intFirstRow To intLastRow
    If DoRules(intRowLoop, firstColumn, lastColumn) Then status = False
Next intRowLoop
    CheckData = status
    
End Function

Public Function DoRules(rowNumber As Integer, startCol As String, endCol As String)

    Dim firstCell As Range
    Dim lastCell As Range
    Dim mySheet As Worksheet
    Dim rCDEid As Boolean
    Dim rDEC As Boolean
    Dim rVD As Boolean
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set firstCell = mySheet.Range(startCol & CStr(rowNumber))
    Set lastCell = mySheet.Range(endCol & CStr(rowNumber))
    
    'firstCell.Value2 = "firstCell" & CStr(rowNumber)
    'lastCell.Value2 = "lastCell" & CStr(rowNumber)
    Call ClearComments(firstCell, lastCell)
    rCDEid = CRuleCDEid(firstCell, lastCell)
    If rCDEid = False Then
        rDEC = CRuleDEC(firstCell, lastCell)
        rVD = CRuleVD(firstCell, lastCell)
    End If
        
    DoRules = rCDEid Or (rDEC And rVD)
    
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
        rng.ClearComments
    Next i

End Sub
Private Function CRuleDEC(fc As Range, lc As Range) As Boolean
    Dim startC As Range
    Dim endC As Range
    Dim vp As Boolean
    
    Set startC = fc.Offset(0, 3)
    Set endC = fc.Offset(0, 9)
    
    If Len(startC.Text) > 0 And ValidPublicId(startC) Then
        If CEmpty(startC.Offset(0, 1), endC, "DEC id is present, this cell will not be processed.") Then
            CRuleDEC = True
        Else
            CRuleDEC = False
        End If
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
    
    If Len(startC.Text) > 0 And ValidPublicId(startC) Then
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
    
    If Len(fc.Text > 0) And ValidPublicId(fc) Then
        Call CEmpty(fc.Offset(0, 1), lc, "CDE id is present, this cell will not be processed.")
        CRuleCDEid = True
    Else
        CRuleCDEid = False
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
        Else
            Call ClearError(rng)
        End If
    Next i
    
    CEmpty = status
End Function

Private Sub SetErrorComment(rng As Range, msg As String)
    rng.Borders.Weight = 3
    rng.Borders.Color = 2
    rng.ClearComments
    rng.AddComment (msg)
    'rng.Comment.Visible = True
    rng.Comment.Shape.TextFrame.AutoSize = True
End Sub

Private Sub ClearError(rng As Range)
    rng.Borders.Weight = 2
    rng.Borders.Color = 0
    rng.ClearComments

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
Private Function ValidPublicId(fc As Range) As Boolean
'Pattern for Concept ID
'Any Digit(0 to inf), space, dot, 'v', dot, single digit, dot, one digit, zero or one digit
Dim testPattern As String
testPattern = "^\d*\sv\.\d\.\d\d?$"
If ValidString(testPattern, fc.Text) Then
    Call ClearError(fc)
    ValidPublicId = True
Else
    Call SetErrorComment(fc, "Not a Valid Id")
    ValidPublicId = False
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

intFirstRow = 5
firstColumn = "F"
lastColumn = "Z"

' determine last row in sheet

Set objExcel = CreateObject("Excel.Application")
Worksheets("Curation Worksheet").UsedRange.SpecialCells(xlCellTypeLastCell).Activate
intLastRow = ActiveCell.Row

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
