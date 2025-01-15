prog    START 0

        

        JSUB sinit



loop    JSUB rd
        COMP #0
        JEQ halt
        JSUB fac
        JSUB prnt

        RD #250
        

        LDA #32
        WD #1
        
        J loop


halt    J halt

fac     STL @sp
        JSUB spush
        

        COMP #1
        JEQ facEnd

        RMO A B
        STB @sp
        JSUB spush

        SUB #1
        JSUB fac
        JSUB spop
        LDB @sp

        MULR B A

facEnd  JSUB spop
        LDL @sp
        RSUB

prnt    STL @sp
        JSUB spush
        STA @sp
        JSUB spush
        STB @sp
        JSUB spush

        RMO A B
        DIV #10
        MUL #10
        SUBR A B
        DIV #10

        COMP #0
        JEQ prntEnd
        JSUB prnt

prntEnd RMO B A
        ADD #48
        WD #1
        JSUB spop
        LDB @sp
        JSUB spop
        LDA @sp
        JSUB spop
        LDL @sp
        RSUB

...................................... metoda za branje stevila v A
rd      LDA #0
rdLoop  STA rdA1
        LDA #0
        RD #250

        SUB #48
        COMP #0 . primerjamo ze z 0, da se lahko odlocimo, ce nadaljujemo loop ali ne
        JLT rdEnd . ce je prebrana vrednost new line, space ali eof, gremo ven
        STA rdA2
        LDA rdA1
        MUL #10
        ADD rdA2
        J rdLoop







rdEnd   LDA rdA1
        RSUB

rdA1    RESW 1
rdA2    RESW 1





sinit   STA tempA    . nastavi vrednost sp na zacetek sklada
        LDA #stack
        STA sp
        LDA tempA
        RSUB


. poveca vrednost sp za eno besedo
spush   STA tempA
        LDA sp
        ADD #3
        STA sp
        LDA tempA
        RSUB
spop    STA tempA
        LDA sp
        SUB #3
        STA sp
        LDA tempA
        RSUB
stack   RESW 1000
sp      WORD 0
tempA   WORD 0
n       WORD 4

        END prog