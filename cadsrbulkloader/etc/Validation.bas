Attribute VB_Name = "Validation"
Sub CheckData()

Dim firstColumn As String
Dim lastColumn As String
Dim iRowLoop As Integer
Dim intLastRow As Integer
Dim intFirstRow As Integer
Dim intRowLoop As Integer

intFirstRow = 5
firstColumn = "F"
lastColumn = "Z"

' determine last row in sheet

Set objExcel = CreateObject("Excel.Application")
Worksheets("Curation Worksheet").UsedRange.SpecialCells(xlCellTypeLastCell).Activate
intLastRow = ActiveCell.row

'start iterating over rows
For intRowLoop = intFirstRow To intLastRow
    Call DoRules(intRowLoop, firstColumn, lastColumn)
Next intRowLoop

End Sub

Public Sub DoRules(rowNumber As Integer, startCol As String, endCol As String)

    Dim firstCell As Range
    Dim lastCell As Range
    Dim mySheet As Worksheet
    
    Set mySheet = Worksheets("Curation Worksheet")
    Set firstCell = mySheet.Range(startCol & CStr(rowNumber))
    Set lastCell = mySheet.Range(endCol & CStr(rowNumber))
    
    'firstCell.Value2 = "firstCell" & CStr(rowNumber)
    'lastCell.Value2 = "lastCell" & CStr(rowNumber)
    
    
    resCDEid = CRuleCDEid(firstCell, lastCell)
    'Call CRuleNoCDEid(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    'Call CRule(firstCell, lastCell)
    
    

End Sub

Private Function CRuleCDEid(fC As Range, lC As Range) As Boolean
    
    If Len(fC.Text > 0 And ValidCDE(fC.Text)) Then
        If CEmpty(fC.Offset(0, 1), lC) Then
            CRuleCDEid = True
        Else
            CRuleCDEid = False
        End If
    End If
    
End Function

Private Function CEmpty(fC As Range, lC As Range) As Boolean
    Dim fCol As Integer
    Dim lCol As Integer
    Dim status As Boolean
    
    fCol = fC.Column
    lCol = lC.Column
    status = True
    
    For i = 0 To lCol - fCol
        If (Len(fC.Offset(0, i).Text) > 0) Then status = False
    Next i
    
    CEmpty = status
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
Private Function ValidCDE(cid As String) As Boolean
'Pattern for Concept ID
'Any Digit(0 to inf), space, dot, 'v', dot, single digit, dot, one digit, zero or one digit
Dim testPattern As String
testPattern = "^\d*\sv\.\d\.\d\d?$"
ValidCDE = ValidString(testPattern, cid)
End Function
