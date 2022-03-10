const content=document.querySelector('#main')
const body=document.querySelector('body')
const main=addClassList(document.createElement('main'), 'd-flex', {
    width: '100vw',
    height: 'calc(100vh - 80px)'
})
let navbarItems=[
    {url: '/dashboard/most-sold', path: '/dashboard',name: 'Dashboard'},
    {url: '/users/user', path: '/users',name: 'Users'},
    {url: '/input/get', path:'/input',name: 'Input'},
    {url: '/output', path: '/output',name: 'Output'},
    {url: '/data', path: '/data',name: 'Data'},
]
let leftNavbarItems={
    'Dashboard': [
        {url:'/dashboard/most-sold', name:'Most sold'},
        {url:'/dashboard/notifications', name:'Notifications'},
        {url:'/dashboard/context', name:'Context'}
    ]
}


function addClassList(element, classList, style={}){
    let classes=classList.split(' ')
    for(let classH of classes){
        element.classList.add(classH)
    }
    for (let styleKey in style) {
        element.style[styleKey]=style[styleKey]
    }
    return element;
}

function createElement(tag='', classList='', styles={}, attributes={}) {
    const element=document.createElement(tag)
    for (let styleClass of classList.split(' ')) {
        if(styleClass===''){continue}
        element.classList.add(styleClass)
    }
    for (let key in styles) {
        element.style[key]=styles[key]
    }
    for (let key in attributes) {
        if(key==='text'){
            element.textContent=attributes[key]
            continue
        }
        element.setAttribute(key, attributes[key])
    }
    return element;
}

function appendElement(element=new Node(), child=new Node()){
    element.appendChild(child)
    return element
}



for (let classStyle of 'text-dark w-100 h-1000'.split(' ')) {
    body.classList.add(classStyle)
}
body.style.overflowY='hidden'
body.style.backgroundColor='#f1f1f1'
const navbar=createElement('nav', 'w-100 bg-white shadow-lg d-flex justify-content-start', {
    height: '80px'
})
let navUl=appendElement(
    createElement('ul',
            'navbar nav justify-content-start align-items-center w-50',
                {},{}),
    appendElement(
        createElement('li', 'nav-item'),
        createElement('img', 'navbar-brand', {},{
            'src':'/assets/favicon.ico',
            'width': '50px',
            'alt': 'warehouse-logo.ico'
        })
    ))
let activeParentNavbarName=''
for (let navbarItem of navbarItems) {
    const li=createElement('li', 'nav-item')
    if(location.pathname.startsWith(navbarItem.path)){
        activeParentNavbarName=navbarItem.name
    }
    navUl.append(appendElement(li,
        createElement('a','nav-link'+(activeParentNavbarName.includes(navbarItem.name)?' active':''), {}, {
            'href': navbarItem.url,
            'text': navbarItem.name
        })))
}
navbar.appendChild(navUl)
body.appendChild(navbar)
const leftNavbar=createElement('div', 'w-25 h-100 shadow py-4', {'overflowY': 'auto'})
if(activeParentNavbarName===''){
    body.innerHTML='<div class="container text-center my-5"><h1>404</h1></div>'
}
for(let obj of leftNavbarItems[activeParentNavbarName]){
    leftNavbar.appendChild(appendElement(
        createElement('div', 'px-5'),
        createElement('a', 'nav-link text-dark link'+(location.pathname.includes(obj.url)?' active':''),
            {},
            {
                href: obj.url,
                text: obj.name
        })
    ))
}

main.appendChild(leftNavbar)
content.classList.add('px-3')
content.classList.add('pt-3')
main.appendChild(appendElement(
    createElement('div', 'w-75 h-100', {overflowY: 'auto'}),
    content
))
body.appendChild(main)



